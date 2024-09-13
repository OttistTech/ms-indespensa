package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    @Query("""
       SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO(
            li.listItemId,\s
            li.user.userId,\s
            f.foodName,\s
            p.imageUrl,\s
            li.amount,\s
            p.amount,\s
            p.unit
       )
       FROM ShopItem li
       JOIN li.product p
       JOIN p.foodId f
       WHERE li.user = :user \s
       AND li.purchaseDate IS NULL
      \s""")
    List<ShopItemResponseDTO> findAllByUser(User user);

    @Query("""
      SELECT
        si \s
      FROM ShopItem si \s
      WHERE si.user = :user \s
      AND si.product = :product \s
      AND si.purchaseDate IS NULL
      \s""")
    Optional<ShopItem> findByUserAndProductWithNullPurchaseDate(User user, Product product);
}
