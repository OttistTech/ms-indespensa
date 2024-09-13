package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ListShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    @Query("""
       SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.ListShopItemResponseDTO(
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
       WHERE li.user = :user
      \s""")
    List<ListShopItemResponseDTO> findAllByUser(User user);
}
