package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.model.Category;
import com.ottistech.indespensa.api.ms_indespensa.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Cacheable(value = "categories", key = "#pattern != null ? #pattern : 'defaultKey'")
    public List<String> listCategories(String pattern) {
        List<Category> result;
        if (pattern == null || pattern.isEmpty()) {
            result = categoryRepository.findAll();
        } else {
            result = categoryRepository.findByCategoryNameContainingIgnoreCase(pattern);
        }

        if(result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories matching the pattern");
        }

        return result.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());
    }

    public Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(categoryName);
                    return categoryRepository.save(newCategory);
                });
    }


}
