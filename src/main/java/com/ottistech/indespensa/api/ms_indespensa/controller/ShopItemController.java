package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.controller.contract.ShopItemContract;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.shop.response.ShopPurchaseHistoryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.ShopItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shop")
public class ShopItemController implements ShopItemContract {

    private final ShopItemService shopItemService;

    @GetMapping("/{user_id}/list")
    public ResponseEntity<List<ShopItemResponseDTO>> getShopItemListInfo(
            @PathVariable("user_id")
            Long userId
    ) {

        List<ShopItemResponseDTO> listItemResponse = shopItemService.getListItem(userId);

        return ResponseEntity.ok(listItemResponse);
    }

    @PostMapping("/{user_id}/add")
    public ResponseEntity<ShopItemResponseDTO> addShopItem(
            @PathVariable("user_id")
            Long userId,

            @Valid
            @RequestBody
            AddShopItemDTO shopItemDTO
    ) {

        ShopItemResponseDTO itemResponseDTO = shopItemService.addShopItem(userId, shopItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseDTO);
    }

    @GetMapping("/{shop_item_id}/details")
    public ResponseEntity<ShopItemDetailsDTO> getShopItem(
            @PathVariable("shop_item_id")
            Long shopItemId
    ) {

        ShopItemDetailsDTO shopItem = shopItemService.getShopItemDetails(shopItemId);

        return ResponseEntity.ok(shopItem);
    }

    @PatchMapping("/update-items-amount")
    public ResponseEntity<Void> updateShopItemsAmount(
            @RequestBody
            @Valid
            List<UpdateProductItemAmountDTO> shopItems
    ) {

        shopItemService.updateShopItemsAmount(shopItems);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{user_id}/list/history")
    public ResponseEntity<List<ShopPurchaseHistoryItemDTO>> getPurchaseHistoryInfo(
            @PathVariable("user_id")
            Long userId
    ) {

        List<ShopPurchaseHistoryItemDTO> historyItems = shopItemService.getPurchaseHistoryItems(userId);
        return ResponseEntity.ok(historyItems);
    }
}
