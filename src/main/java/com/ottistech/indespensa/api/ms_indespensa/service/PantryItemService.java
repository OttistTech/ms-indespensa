package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemSimplifiedResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.PantryItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PantryItemService {

    private final PantryItemRepository pantryItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public PantryItemSimplifiedResponseDTO createPantryItem(Long userId, CreatePantryItemDTO pantryItemDTO) {
        Product product = productService.getOrCreateProduct(pantryItemDTO.toProductDto());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found and can't add an item to his pantry"));

        Optional<PantryItem> pantryItemOptional = pantryItemRepository.findExistentPantryItem(user, product, pantryItemDTO.validityDate());
        PantryItem pantryItem;

        if(pantryItemOptional.isEmpty()) {
            pantryItem = new PantryItem();

            pantryItem.setUser(user);
            pantryItem.setProduct(product);
            pantryItem.setAmount(pantryItemDTO.pantryAmount());
            pantryItem.setValidityDate(pantryItemDTO.validityDate());
        } else {
            pantryItem = pantryItemOptional.get();
            pantryItem.setAmount(pantryItem.getAmount() + pantryItemDTO.pantryAmount());
            pantryItem.setPurchaseDate(LocalDate.now());
        }

        pantryItemRepository.save(pantryItem);

        return PantryItemSimplifiedResponseDTO.fromPantryItem(pantryItem);
    }

    public List<PantryItemPartialDTO> listPantryItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        List<PantryItemPartialDTO> userActivePantryItems = pantryItemRepository.findAllActiveItemsByUser(user);
        if(userActivePantryItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry items found for the given userId");
        }

        return userActivePantryItems;
    }

    public List<PantryItem> updatePantryItemsAmount(List<UpdateProductItemAmountDTO> pantryItems) {
        List<PantryItem> updatedItems = new ArrayList<>();

        for(UpdateProductItemAmountDTO itemUpdate : pantryItems) {
            PantryItem item = pantryItemRepository.findById(itemUpdate.itemId()).orElse(null);

            if(item != null) {
                item.setAmount(itemUpdate.amount());

                if(item.getAmount() == 0) {
                    item.setIsActive(false);
                }

                updatedItems.add(item);
            }
        }

        pantryItemRepository.saveAll(updatedItems);

        return updatedItems;
    }

    public PantryItemDetailsDTO getPantryItemDetails(Long pantryItemId) {
        return pantryItemRepository.findPantryItemDetailsById(pantryItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pantry item matching the given id"));
    }
}
