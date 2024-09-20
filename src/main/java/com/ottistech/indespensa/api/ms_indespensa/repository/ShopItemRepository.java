package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    List<ShopItem> findAllByUserUserId(Long userId);

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
    List<ShopItemResponseDTO> findAllShopItemResponseDTOByUser(Long userId);

    @Query("""
      SELECT
        si
      FROM ShopItem si
      WHERE si.user.userId = :userId
      AND si.product.productId = :productId
      AND si.purchaseDate IS NULL
      """)
    Optional<ShopItem> findByUserAndProductWithNullPurchaseDate(Long userId, Long productId);

    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemDetailsDTO(
                si.listItemId,
                si.user.userId,
                si.product.productId,
                si.product.imageUrl,
                si.product.foodId.foodName,
                si.product.name,
                si.product.brandId.brandName,
                si.product.description,
                si.product.amount,
                si.product.unit,
                si.amount
            ) FROM ShopItem si
            JOIN si.product p
            JOIN si.user u
            JOIN p.foodId f
            JOIN p.brandId b
            WHERE si.listItemId = :shopItemId
            AND si.purchaseDate IS NULL
            """)
    Optional<ShopItemDetailsDTO> findShopItemDetailsById(Long shopItemId);
}
