package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.PartialPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.PartialPantryItemDTO(
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
    List<PartialPantryItemDTO> findAllActiveItemsByUser(User user);
}
