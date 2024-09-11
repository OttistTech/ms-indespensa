package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<?> listCategories(
            @RequestParam(value = "match", required = false) String pattern) {

        List<String> categoriesFound = categoryService.listCategories(pattern);

        return ResponseEntity.status(HttpStatus.OK).body(categoriesFound);
    }

}
