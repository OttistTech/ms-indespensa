package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.controller.contract.PantryItemContract;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.AddPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemsNextToValidityDate;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.service.PantryItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/pantry")
public class PantryItemController implements PantryItemContract {

    private final PantryItemService pantryItemService;

    @PostMapping("/{user_id}/create")
    public ResponseEntity<PantryItemResponseDTO> createPantryItem(
            @PathVariable("user_id")
            Long userId,

            @RequestBody
            @Valid
            CreatePantryItemDTO pantryItem
    ) {

        PantryItemResponseDTO pantryItemSimplifiedResponseDTO = pantryItemService.createPantryItem(userId, pantryItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(pantryItemSimplifiedResponseDTO);
    }

    @GetMapping("/{user_id}/list")
    public ResponseEntity<List<PantryItemPartialDTO>> listPantryItems(
            @PathVariable("user_id")
            Long userId
    ) {

        List<PantryItemPartialDTO> userActivePantryItems = pantryItemService.listPantryItems(userId);

        return ResponseEntity.ok(userActivePantryItems);
    }

    @PatchMapping("/update-items-amount")
    public ResponseEntity<List<PantryItem>> updatePantryItemsAmount(
            @RequestBody @Valid List<UpdateProductItemAmountDTO> pantryItems
    ) {

        List<PantryItem> updatedItems = pantryItemService.updatePantryItemsAmount(pantryItems);

        return ResponseEntity.ok(updatedItems);
    }

    @GetMapping("/{pantry_item_id}/details")
    public ResponseEntity<PantryItemDetailsDTO> getPantryItem(
            @PathVariable("pantry_item_id")
            Long pantryItemId
    ) {

        PantryItemDetailsDTO pantryItem = pantryItemService.getPantryItemDetails(pantryItemId);

        return ResponseEntity.ok(pantryItem);
    }

    @PostMapping("/{user_id}/add-all")
    public ResponseEntity<Void> addAllShopItemsToPantry(
            @PathVariable("user_id")
            Long userId
    ) {

        pantryItemService.addAllFromShopList(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{user_id}/add")
    public ResponseEntity<PantryItemResponseDTO> addPantryItem(
            @PathVariable("user_id")
            Long userId,

            @Valid
            @RequestBody
            AddPantryItemDTO pantryItemDTO
    ) {

        PantryItemResponseDTO itemResponseDTO = pantryItemService.addPantryItem(userId, pantryItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseDTO);
    }

    @GetMapping("/{user_id}/soon-to-expire")
    public ResponseEntity<List<PantryItemsNextToValidityDate>> findExpiringPantryItemsByUser(
            @PathVariable("user_id")
            Long userId
    ) {

        List<PantryItemsNextToValidityDate> itemsNextToValidityDate = pantryItemService.getPantryItemsNextToValidityDate(userId);

        return ResponseEntity.ok(itemsNextToValidityDate);
    }

}
