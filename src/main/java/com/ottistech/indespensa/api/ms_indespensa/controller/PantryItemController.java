package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.PantryItemCreateDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemSimplifiedResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO;
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
public class PantryItemController {

    private final PantryItemService pantryItemService;

    @PostMapping("/{user_id}/create")
    public ResponseEntity<?> createPantryItem(@PathVariable("user_id") Long userId, @RequestBody @Valid PantryItemCreateDTO pantryItem) {

        PantryItemSimplifiedResponseDTO pantryItemSimplifiedResponseDTO = pantryItemService.createPantryItem(userId, pantryItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(pantryItemSimplifiedResponseDTO);
    }

    @GetMapping("/{user_id}/list")
    public ResponseEntity<List<PantryItemPartialDTO>> listPantryItems(@PathVariable("user_id") Long userId) {

        List<PantryItemPartialDTO> userActivePantryItems = pantryItemService.listPantryItems(userId);

        return ResponseEntity.ok(userActivePantryItems);
    }

    @PatchMapping("/update-items-amount")
    public ResponseEntity<List<PantryItem>> updatePantryItemsAmount(@RequestBody @Valid List<UpdatePantryItemDTO> pantryItems) {

        List<PantryItem> updatedItems = pantryItemService.updatePantryItemsAmount(pantryItems);

        return ResponseEntity.ok(updatedItems);
    }

    @GetMapping("/{pantry_item_id}/details")
    public ResponseEntity<PantryItemDetailsDTO> getPantryItem(@PathVariable("pantry_item_id") Long pantryItemId) {

        PantryItemDetailsDTO pantryItem = pantryItemService.getPantryItemDetails(pantryItemId);

        return ResponseEntity.ok(pantryItem);
    }
}
