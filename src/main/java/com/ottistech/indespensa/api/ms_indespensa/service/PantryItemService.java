package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.AddPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemsNextToValidityDate;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.PantryItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.ottistech.indespensa.api.ms_indespensa.utils.Constants.DAYS_FROM_NOW;

@AllArgsConstructor
@Service
public class PantryItemService {

    private final PantryItemRepository pantryItemRepository;
    private final ShopItemRepository shopItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    @Transactional
    @CacheEvict(value = "pantry_items", key = "#userId")
    public PantryItemResponseDTO createPantryItem(Long userId, CreatePantryItemDTO pantryItemDTO) {
        Product product = productService.getOrCreateProduct(pantryItemDTO.toProductDto());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found and can't add an item to his pantry"));

        Optional<PantryItem> pantryItemOptional = pantryItemRepository.findExistentPantryItem(user, product, pantryItemDTO.validityDate());
        PantryItem pantryItem;

        if(pantryItemOptional.isEmpty()) {
            pantryItem = new PantryItem();

            pantryItem.setUser(user);
            pantryItem.setProduct(product);
            pantryItem.setAmount(pantryItemDTO.pantryAmount());
            pantryItem.setValidityDate(pantryItemDTO.validityDate());
            pantryItem.setIsActive(true);
        } else {
            pantryItem = pantryItemOptional.get();
            pantryItem.setAmount(pantryItem.getAmount() + pantryItemDTO.pantryAmount());
            pantryItem.setPurchaseDate(LocalDate.now());
        }

        AddShopItemDTO addShopItemDTO = pantryItemDTO.toAddShopItemDTO(pantryItem.getProduct().getProductId());
        shopItemRepository.save(addShopItemDTO.toShopItem(user, product, addShopItemDTO, LocalDate.now()));

        pantryItemRepository.save(pantryItem);

        return PantryItemResponseDTO.fromPantryItem(pantryItem);
    }

    public List<PantryItemPartialDTO> listPantryItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        List<PantryItemPartialDTO> userActivePantryItems = pantryItemRepository.findAllActiveItemsByUser(user);
        if(userActivePantryItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry items found for the given userId");
        }

        return userActivePantryItems;
    }

    @Transactional
    @CacheEvict(value = {"pantry_items", "pantry_item_details"}, allEntries = true)
    public void updatePantryItemsAmount(List<UpdateProductItemAmountDTO> pantryItems) {
        List<Long> itemIds = pantryItems.stream()
                .map(UpdateProductItemAmountDTO::itemId)
                .collect(Collectors.toList());

        List<PantryItem> items = pantryItemRepository.findAllById(itemIds);

        Map<Long, Integer> itemAmounts = pantryItems.stream()
                .collect(Collectors.toMap(UpdateProductItemAmountDTO::itemId, UpdateProductItemAmountDTO::amount));

        items.forEach(item -> {
            Integer newAmount = itemAmounts.get(item.getPantryItemId());
            item.setAmount(newAmount);

            item.setIsActive(newAmount == null || newAmount != 0);
        });

        pantryItemRepository.saveAll(items);
    }

    public PantryItemDetailsDTO getPantryItemDetails(Long pantryItemId) {
        return pantryItemRepository.findPantryItemDetailsById(pantryItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry item matching the given id"));
    }

    @Transactional
    @CacheEvict(value = {"pantry_items", "pantry_item_details", "shop_items_list"}, allEntries = true)
    public void addAllFromShopList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        List<ShopItem> userShopItems = shopItemRepository.findAllByUserUserIdAndPurchaseDateIsNullAndAmountGreaterThan(userId, 0);

        if (userShopItems.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have any shop items in his shopping list");

        for (ShopItem shopItem : userShopItems) {
            Optional<PantryItem> existingPantryItemOpt = pantryItemRepository.findByUserUserIdAndProductProductIdAndIsActiveAndValidityDateIsNull(
                    userId, shopItem.getProduct().getProductId(), true
            );

            if (existingPantryItemOpt.isPresent()) {
                PantryItem existingPantryItem = existingPantryItemOpt.get();
                existingPantryItem.setAmount(existingPantryItem.getAmount() + shopItem.getAmount());
                pantryItemRepository.save(existingPantryItem);
            } else {
                PantryItem newPantryItem = new PantryItem();

                newPantryItem.setUser(user);
                newPantryItem.setProduct(shopItem.getProduct());
                newPantryItem.setAmount(shopItem.getAmount());
                newPantryItem.setIsActive(true);
                pantryItemRepository.save(newPantryItem);
            }

            shopItem.setPurchaseDate(LocalDate.now());
        }

        shopItemRepository.saveAll(userShopItems);
    }

    @Transactional
    @CacheEvict(value = {"pantry_items", "pantry_item_details"}, allEntries = true)
    public PantryItemResponseDTO addPantryItem(Long userId, AddPantryItemDTO pantryItemDTO) {
        ShopItem shopItem = shopItemRepository.findById(pantryItemDTO.shopItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop list item not found"));

        if(!Objects.equals(shopItem.getUser().getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided user id does not match shop list item user id");
        }

        PantryItem pantryItem = pantryItemRepository
            .findByUserIdAndProductIdAndValidityDateWhereIsActive(
                shopItem.getUser().getUserId(),
                shopItem.getProduct().getProductId(),
                pantryItemDTO.validityDate()
            )
            .orElse(null);

        if (pantryItem != null) {
            pantryItem.setAmount(pantryItem.getAmount() + shopItem.getAmount());
        } else {
            pantryItem = pantryItemDTO.toPantryItem(
                    shopItem.getUser(),
                    shopItem.getProduct(),
                    shopItem.getAmount(),
                    pantryItemDTO.validityDate()
            );
        }

        shopItem.setPurchaseDate(LocalDate.now());
        pantryItem.setIsActive(true);

        pantryItemRepository.save(pantryItem);
        shopItemRepository.save(shopItem);

        return PantryItemResponseDTO.fromPantryItem(pantryItem);
    }

    public List<PantryItemsNextToValidityDate> getPantryItemsNextToValidityDate(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exists")
        );

        List<PantryItemsNextToValidityDate> itemsNextToValidityDate = pantryItemRepository.findAllItemsWithValidityWithinNextProvidedDays(user, LocalDate.now(), LocalDate.now().plusDays(DAYS_FROM_NOW));

        if (itemsNextToValidityDate.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry items next to validity date");

        return itemsNextToValidityDate;

    }
}
