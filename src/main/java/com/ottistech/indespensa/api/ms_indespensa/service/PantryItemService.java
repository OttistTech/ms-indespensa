package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.PantryItemSimplifiedResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.PartialPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.UpdatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.PantryItemNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.exception.UserNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.model.*;
import com.ottistech.indespensa.api.ms_indespensa.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PantryItemService {

    private final PantryItemRepository pantryItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;

    public PantryItemSimplifiedResponseDTO createPantryItem(Long userId, CreatePantryItemDTO pantryItemDTO) {

        Product product = getOrCreateProduct(pantryItemDTO);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found and can't add an item to his pantry"));

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

        return new PantryItemSimplifiedResponseDTO(
                pantryItem.getProduct().getEanCode(),
                pantryItem.getProduct().getName(),
                pantryItem.getProduct().getImageUrl(),
                pantryItem.getProduct().getFoodId().getFoodName(),
                pantryItem.getProduct().getCategoryId().getCategoryName(),
                pantryItem.getProduct().getDescription(),
                pantryItem.getProduct().getBrandId().getBrandName(),
                pantryItem.getProduct().getAmount(),
                pantryItem.getProduct().getUnit(),
                pantryItem.getProduct().getType(),
                pantryItem.getUser().getUserId(),
                pantryItem.getAmount(),
                pantryItem.getValidityDate(),
                pantryItem.getPurchaseDate(),
                pantryItem.getIsActive()
        );
    }

    private Product getOrCreateProduct(CreatePantryItemDTO pantryItemDTO) {
        Optional<Product> existingProductByEanCode = productRepository.findByEanCodeNotNull(pantryItemDTO.productEanCode());

        if (existingProductByEanCode.isPresent()) {
            return existingProductByEanCode.get();
        }

        Optional<Product> existingProductByName = productRepository.findByNameNotNull(pantryItemDTO.productName());

        return existingProductByName.orElseGet(() -> createNewProduct(pantryItemDTO));
    }

    private Product createNewProduct(CreatePantryItemDTO pantryItemDTO) {
        Product product = new Product();

        product.setEanCode(pantryItemDTO.productEanCode());
        product.setName(pantryItemDTO.productName());
        product.setImageUrl(pantryItemDTO.productImageUrl());
        product.setDescription(pantryItemDTO.productDescription());
        product.setAmount(pantryItemDTO.productAmount());
        product.setUnit(pantryItemDTO.productUnit());

        product.setFoodId(getOrCreateFood(pantryItemDTO.foodName()));
        product.setBrandId(getOrCreateBrand(pantryItemDTO.brandName()));
        product.setCategoryId(getOrCreateCategory(pantryItemDTO.categoryName()));

        productRepository.save(product);

        return product;
    }

    private Food getOrCreateFood(String foodName) {
        return foodRepository.findByFoodName(foodName)
                .orElseGet(() -> {
                    Food newFood = new Food();
                    newFood.setFoodName(foodName);
                    return foodRepository.save(newFood);
                });
    }

    private Brand getOrCreateBrand(String brandName) {
        return brandRepository.findByBrandName(brandName)
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setBrandName(brandName);
                    return brandRepository.save(newBrand);
                });
    }

    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(categoryName);
                    return categoryRepository.save(newCategory);
                });
    }

    public List<PartialPantryItemDTO> listPantryItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist")); 

        List<PartialPantryItemDTO> userActivePantryItems = pantryItemRepository.findAllActiveItemsByUser(user);
        if(userActivePantryItems.isEmpty()) {
            throw new PantryItemNotFoundException("No pantry items found for the giver userId");
        }

        return userActivePantryItems;
    }

    public List<PantryItem> updatePantryItemsAmount(List<UpdatePantryItemDTO> pantryItems) {
        List<PantryItem> updatedItems = new ArrayList<>();
        for(UpdatePantryItemDTO itemUpdate : pantryItems) {
            PantryItem item = pantryItemRepository.findById(itemUpdate.pantryItemId()).orElse(null);
            if(item != null) {
                item.setAmount(itemUpdate.pantryAmount());
                if(item.getAmount() == 0) {
                    item.setIsActive(false);
                }
                updatedItems.add(item);
            }
        }
        pantryItemRepository.saveAll(updatedItems);
        return updatedItems;
    }
}
