package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ListShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.ShopItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shopping-item")
public class ShopItemController {

    private final ShopItemService shopItemService;

    @GetMapping("/{user_id}/list")
    public ResponseEntity<List<ListShopItemResponseDTO>> getShopItemListInfo(@PathVariable("user_id") Long userId) {

        List<ListShopItemResponseDTO> listItemResponse = shopItemService.getListItem(userId);

        return ResponseEntity.ok(listItemResponse);
    }
}
