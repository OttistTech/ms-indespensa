package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
}
