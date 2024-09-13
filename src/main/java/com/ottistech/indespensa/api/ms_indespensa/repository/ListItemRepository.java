package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.ListItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ListItem;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {

    @Query("""
       SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.ListItemResponseDTO(
           f.foodName,\s
           p.imageUrl,\s
           li.amount,\s
           p.amount,\s
           p.unit
       )
       FROM ListItem li
       JOIN li.product p
       JOIN p.foodId f
       WHERE li.user = :user
      \s""")
    List<ListItemResponseDTO> findAllByUser(User user);
}
