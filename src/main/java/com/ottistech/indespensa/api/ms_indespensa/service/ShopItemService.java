package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopPurchaseHistoryDataDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopPurchaseHistoryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.ProductRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

    public List<ShopItemResponseDTO> getListItem(Long userId) {
        List<ShopItemResponseDTO> listItemResponses = shopItemRepository.findAllShopItemResponseDTOByUser(userId);

        if (listItemResponses.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items found or user doesnt exists");

        return listItemResponses;
    }

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

    public ShopItemDetailsDTO getShopItemDetails(Long shopItemId) {
        return shopItemRepository.findShopItemDetailsById(shopItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No shop item matching the given id"));
    }

    public List<ShopItem> updateShopItemsAmount(List<UpdateProductItemAmountDTO> shopItems) {
        List<ShopItem> updatedItems = new ArrayList<>();

        for(UpdateProductItemAmountDTO itemUpdate : shopItems) {
            ShopItem item = shopItemRepository.findById(itemUpdate.itemId()).orElse(null);

            if(item != null) {
                item.setAmount(itemUpdate.amount());
                updatedItems.add(item);
            }
        }

        shopItemRepository.saveAll(updatedItems);

        return updatedItems;
    }

    public List<ShopPurchaseHistoryItemDTO> getPurchaseHistoryItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exists"));

        List<Object[]> results = shopItemRepository.findAllPurchaseHistoryItemsByUserId(userId);

        if (results.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No purchase history items found");

        Map<LocalDate, List<ShopPurchaseHistoryDataDTO>> historyMap =
                results.stream()
                        .collect(Collectors.groupingBy(
                                result -> (LocalDate) result[0],
                                Collectors.mapping(result -> new ShopPurchaseHistoryDataDTO(
                                        (String) result[1],
                                        (String) result[2],
                                        (Long) result[3]
                                ), Collectors.toList())
                        ));

        return historyMap.entrySet().stream()
                .map(entry -> new ShopPurchaseHistoryItemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
