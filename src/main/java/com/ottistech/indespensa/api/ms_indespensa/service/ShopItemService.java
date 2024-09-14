package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.UserNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.ProductRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class ShopItemService {

    private final UserRepository userRepository;
    private final ShopItemRepository shopItemRepository;
    private final ProductRepository productRepository;

    public List<ShopItemResponseDTO> getListItem(Long userId) {
        List<ShopItemResponseDTO> listItemResponses = shopItemRepository.findAllByUser(userId);

        if (listItemResponses.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items found or user doesnt exists");

        return listItemResponses;
    }

    public ShopItemResponseDTO addShopItem(Long userId, AddShopItemDTO shopItemDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        Product product = productRepository.findById(shopItemDTO.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found for this id"));

        ShopItem shopItem = shopItemRepository.findByUserAndProductWithNullPurchaseDate(userId, shopItemDTO.productId())
                .orElse(null);

        if (shopItem != null) {
            shopItem.setAmount(shopItem.getAmount() + shopItemDTO.amount());
        } else {
            shopItem = shopItemDTO.toShopItem(user, product, shopItemDTO);
        }

        shopItemRepository.save(shopItem);

        return shopItemDTO.toShopItemResponseDto(shopItem);
    }

}
