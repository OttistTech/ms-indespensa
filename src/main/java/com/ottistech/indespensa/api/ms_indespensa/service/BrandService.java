package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.model.Brand;
import com.ottistech.indespensa.api.ms_indespensa.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand getOrCreateBrand(String brandName) {
        return brandRepository.findByBrandName(brandName)
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setBrandName(brandName);
                    return brandRepository.save(newBrand);
                });
    }

}
