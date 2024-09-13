package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.PantryItemSimplifiedResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.PartialPantryItemDTO;
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
    public ResponseEntity<?> createPantryItem(@PathVariable("user_id") Long userId, @RequestBody @Valid CreatePantryItemDTO pantryItemDTO) {

        PantryItemSimplifiedResponseDTO pantryItemSimplifiedResponseDTO = pantryItemService.createPantryItem(userId, pantryItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(pantryItemSimplifiedResponseDTO);
    }

    @GetMapping("/{user_id}/list")
    public ResponseEntity<List<PartialPantryItemDTO>> listPantryItems(@PathVariable("user_id") Long userId) {

        List<PartialPantryItemDTO> userActivePantryItems = pantryItemService.listPantryItems(userId);

        return ResponseEntity.ok(userActivePantryItems);
    }
}
