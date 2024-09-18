package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.model.Food;
import com.ottistech.indespensa.api.ms_indespensa.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public Food getOrCreateFood(String foodName) {
        return foodRepository.findByFoodName(foodName)
                .orElseGet(() -> {
                    Food newFood = new Food();
                    newFood.setFoodName(foodName);
                    return foodRepository.save(newFood);
                });
    }

}
