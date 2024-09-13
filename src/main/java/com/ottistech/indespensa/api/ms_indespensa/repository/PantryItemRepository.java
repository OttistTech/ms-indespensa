package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    @Query("SELECT p FROM PantryItem p WHERE p.user.userId = :userId AND p.isActive = true")
    List<PantryItem> findAllActiveItemsByUserId(Long userId);
}
