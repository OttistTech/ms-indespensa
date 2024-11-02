package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.query.ShopPurchaseHistoryDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopPurchaseHistoryDataDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopPurchaseHistoryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.ProductRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShopItemService {

    private final UserRepository userRepository;
    private final ShopItemRepository shopItemRepository;
    private final ProductRepository productRepository;

    @Cacheable(value = "shop_items_list", key = "#userId")
    public List<ShopItemResponseDTO> getListItem(Long userId) {
        List<ShopItemResponseDTO> listItemResponses = shopItemRepository.findAllShopItemResponseDTOByUser(userId);

        if (listItemResponses.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items found or user doesnt exists");

        return listItemResponses;
    }

    @CacheEvict(value = "shop_items_list", key = "#userId")
    @Transactional
    public ShopItemResponseDTO addShopItem(Long userId, AddShopItemDTO shopItemDTO) {
        ShopItem shopItem = shopItemRepository.findByUserAndProductWithNullPurchaseDate(userId, shopItemDTO.productId())
                .orElse(null);

        if (shopItem != null) {
            shopItem.setAmount(shopItem.getAmount() + shopItemDTO.amount());
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exists"));

            Product product = productRepository.findById(shopItemDTO.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found with this id"));

            shopItem = shopItemDTO.toShopItem(user, product, shopItemDTO);
        }

        shopItemRepository.save(shopItem);

        return shopItemDTO.toShopItemResponseDto(shopItem);
    }

    @Cacheable(value = "shop_items_details", key = "#shopItemId")
    public ShopItemDetailsDTO getShopItemDetails(Long shopItemId) {
        return shopItemRepository.findShopItemDetailsById(shopItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No shop item matching the given id"));
    }

    @CacheEvict(value = {"shop_items_details", "shop_items_list", "shop_items_purchase_history"}, allEntries = true)
    public void updateShopItemsAmount(List<UpdateProductItemAmountDTO> shopItems) {
        List<Long> itemIds = shopItems.stream()
                .map(UpdateProductItemAmountDTO::itemId)
                .collect(Collectors.toList());

        List<ShopItem> items = shopItemRepository.findAllById(itemIds);

        Map<Long, Integer> itemAmounts = shopItems.stream()
                .collect(Collectors.toMap(UpdateProductItemAmountDTO::itemId, UpdateProductItemAmountDTO::amount));

        items.forEach(item -> item.setAmount(itemAmounts.get(item.getListItemId())));

        shopItemRepository.saveAll(items);
    }

    public List<ShopPurchaseHistoryItemDTO> getPurchaseHistoryItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exists"));

        List<ShopPurchaseHistoryDTO> allPurchaseHistoryItems = shopItemRepository.findAllPurchaseHistoryItemsByUserId(userId);

        if (allPurchaseHistoryItems.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No purchase history items found");

        Map<LocalDate, List<ShopPurchaseHistoryDataDTO>> historyMap =
                allPurchaseHistoryItems.stream()
                        .collect(Collectors.groupingBy(
                                ShopPurchaseHistoryDTO::purchaseDate,
                                LinkedHashMap::new,
                                Collectors.mapping(result -> new ShopPurchaseHistoryDataDTO(
                                        result.productId(),
                                        result.name(),
                                        result.imageUrl(),
                                        result.amount()
                                ), Collectors.toList())
                        ));

        return historyMap.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, List<ShopPurchaseHistoryDataDTO>>comparingByKey().reversed())
                .map(entry -> new ShopPurchaseHistoryItemDTO(
                        entry.getKey(),
                        entry.getValue().size(),
                        entry.getValue())
                )
                .collect(Collectors.toList());
    }
}
