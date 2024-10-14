package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.*;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.PantryItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    // TODO: verify how to store it
//    @Cacheable(value = "pantry_items", key = "#userId")
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
    public List<PantryItem> updatePantryItemsAmount(List<UpdateProductItemAmountDTO> pantryItems) {
        List<PantryItem> updatedItems = new ArrayList<>();

        for(UpdateProductItemAmountDTO itemUpdate : pantryItems) {
            PantryItem item = pantryItemRepository.findById(itemUpdate.itemId()).orElse(null);

            if(item != null) {
                item.setAmount(itemUpdate.amount());

                if(item.getAmount() == 0) {
                    item.setIsActive(false);
                }

                updatedItems.add(item);
            }
        }

        pantryItemRepository.saveAll(updatedItems);

        return updatedItems;
    }


    // @Cacheable(value = "pantry_item_details", key = "#pantryItemId")
    public PantryItemDetailsDTO getPantryItemDetails(Long pantryItemId) {
        return pantryItemRepository.findPantryItemDetailsById(pantryItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry item matching the given id"));
    }

    @Transactional
    @CacheEvict(value = {"pantry_items", "pantry_item_details"}, allEntries = true)
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

            shopItem.setAmount(0);
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

        pantryItemRepository.save(pantryItem);
        shopItemRepository.save(shopItem);

        return PantryItemResponseDTO.fromPantryItem(pantryItem);
    }

//    @Cacheable(value = "pantry_items_dash_info", key = "#userId")
    public PantryItemDashInfoDTO getPantryItemDashInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        Integer itemCount = pantryItemRepository.countAllActiveItemsByUser(user);
        LocalDate lastPurchaseDate = pantryItemRepository.getLastPurchaseDate(user);
        Integer itemsCloseToExpirationDateCount = pantryItemRepository.countAllItemsWithValidityWithinNextThreeDays(user, LocalDate.now(), LocalDate.now().plusDays(3));
        Integer possibleRecipes = pantryItemRepository.countAllPossibleRecipes(user.getUserId());

        return new PantryItemDashInfoDTO(
                itemCount,
                lastPurchaseDate,
                itemsCloseToExpirationDateCount,
                possibleRecipes
        );

    }
}
