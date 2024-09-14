package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    @Query("""
       SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO(
            si.listItemId,
            si.user.userId,
            f.foodName,
            p.imageUrl,
            si.amount,
            p.amount,
            p.unit
       )
       FROM ShopItem si
       JOIN si.product p
       JOIN p.foodId f
       WHERE si.user.userId = :userId
       AND si.purchaseDate IS NULL
      \s""")
    List<ShopItemResponseDTO> findAllByUser(Long userId);

    @Query("""
      SELECT
        si
      FROM ShopItem si
      WHERE si.user.userId = :userId
      AND si.product.productId = :productId
      AND si.purchaseDate IS NULL
      \s""")
    Optional<ShopItem> findByUserAndProductWithNullPurchaseDate(Long userId, Long productId);
}
