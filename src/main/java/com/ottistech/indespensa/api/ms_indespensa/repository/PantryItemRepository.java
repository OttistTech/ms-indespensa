package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.PartialPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Query("""
        SELECT pi
        FROM PantryItem pi
        WHERE pi.product = :product
        AND pi.user = :user
        AND pi.validityDate = :validityDate
        AND pi.isActive = true
        """)
    Optional<PantryItem> findExistentPantryItem(User user, Product product, LocalDate validityDate);
}
