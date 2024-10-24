package com.ottistech.indespensa.api.ms_indespensa.dto.shop.request;

import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "DTO for adding an item to the shopping list.")
public record AddShopItemDTO(

        @Schema(description = "The ID of the product to add to the shopping list.", example = "321")
        @NotNull(message = "Field productId is required")
        @Min(value = 1, message = "Field productId must be at least 1")
        Long productId,

        @Schema(description = "The quantity of the product to add.", example = "2")
        @NotNull(message = "Field amount is required")
        @Min(value = 1, message = "Field amount must be at least 1")
        Integer amount
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
