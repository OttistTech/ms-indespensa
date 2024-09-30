package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO;
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
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemPartialDTO(
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
            AND pi.amount > 0
            ORDER BY pi.validityDate
            """)
    List<PantryItemPartialDTO> findAllActiveItemsByUser(User user);

    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.PantryItemDetailsDTO(
                pi.pantryItemId,
                pi.user.userId,
                pi.product.productId,
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

    @Query("""
        SELECT pi
        FROM PantryItem pi
        WHERE pi.product = :product
        AND pi.user = :user
        AND pi.validityDate = :validityDate
        AND pi.isActive = true
        """)
    Optional<PantryItem> findExistentPantryItem(User user, Product product, LocalDate validityDate);

    @Query("""
        SELECT pi
        FROM PantryItem pi
        WHERE pi.user.userId = :userId
        AND pi.product.productId = :productId
        AND pi.validityDate = :validityDate
        AND pi.isActive = true
        """)
    Optional<PantryItem> findByUserIdAndProductIdAndValidityDateWhereIsActive(Long userId, Long productId, LocalDate validityDate);

    Optional<PantryItem> findByUserUserIdAndProductProductIdAndIsActiveAndValidityDateIsNull(Long userId, Long productId, boolean isActive);
}
