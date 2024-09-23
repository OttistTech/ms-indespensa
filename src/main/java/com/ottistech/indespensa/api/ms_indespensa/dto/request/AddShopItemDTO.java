package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddShopItemDTO(
        @NotNull(message = "Field productId is required") @Min(value = 1, message = "Field productId must be at least 1") Long productId,
        @NotNull(message = "Field amount is required") @Min(value = 1, message = "Field amount must be at least 1") Integer amount
) {

    public ShopItem toShopItem(User user, Product product, AddShopItemDTO shopItemDTO) {
        return new ShopItem(
                user,
                product,
                shopItemDTO.amount
        );
    }

    public ShopItem toShopItem(User user, Product product, AddShopItemDTO shopItemDTO, LocalDate purchaseDate) {
        return new ShopItem(
                user,
                product,
                shopItemDTO.amount,
                purchaseDate
        );
    }

    public ShopItemResponseDTO toShopItemResponseDto(ShopItem shopItem) {
        return new ShopItemResponseDTO(
                shopItem.getListItemId(),
                shopItem.getUser().getUserId(),
                shopItem.getProduct().getName(),
                shopItem.getProduct().getImageUrl(),
                shopItem.getAmount(),
                shopItem.getProduct().getAmount(),
                shopItem.getProduct().getUnit()
        );
    }
}
