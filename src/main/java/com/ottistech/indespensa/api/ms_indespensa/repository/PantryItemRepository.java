package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.PantryItemPartialDTO(
                pi.user.userId,
                pi.pantryItemId,
                pi.product.name,
                pi.product.imageUrl,
                pi.product.amount,
                pi.product.unit,
                pi.amount,
                pi.validityDate
            ) FROM PantryItem pi
            JOIN pi.product p
            JOIN p.foodId f
            WHERE pi.user = :user
            AND pi.isActive = true
            ORDER BY pi.validityDate
            """)
    List<PantryItemPartialDTO> findAllActiveItemsByUser(User user);

    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO(
                pi.pantryItemId,
                pi.user.userId,
                pi.product.imageUrl,
                pi.product.foodId.foodName,
                pi.product.name,
                pi.product.brandId.brandName,
                pi.product.description,
                pi.product.amount,
                pi.product.unit,
                pi.amount,
                pi.validityDate
            ) FROM PantryItem pi
            JOIN pi.product p
            JOIN pi.user u
            JOIN p.foodId f
            JOIN p.brandId b
            WHERE pi.pantryItemId = :pantryItemId
            AND pi.isActive = true
            """)
    Optional<PantryItemDetailsDTO> findPantryItemDetailsById(Long pantryItemId);
}
