package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ListItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.ListItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/list_item")
public class ListItemController {

    private final ListItemService listItemService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<ListItemResponseDTO>> getListItemInfo(@PathVariable("user_id") Long userId) {

        List<ListItemResponseDTO> listItemResponse = listItemService.getListItem(userId);

        return ResponseEntity.ok(listItemResponse);
    }
}
