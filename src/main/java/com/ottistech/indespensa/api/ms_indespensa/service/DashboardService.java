package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.DashboardInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.PantryItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final PantryItemRepository pantryItemRepository;

    public DashboardInfoDTO getPantryItemDashInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        Integer itemCount = pantryItemRepository.countAllActiveItemsByUser(user);
        LocalDate lastPurchaseDate = pantryItemRepository.getLastPurchaseDate(user);
        Integer itemsCloseToExpirationDateCount = pantryItemRepository.countAllItemsWithValidityWithinNextThreeDays(user, LocalDate.now(), LocalDate.now().plusDays(3));
        Integer possibleRecipes = pantryItemRepository.countAllPossibleRecipes(user.getUserId());

        return new DashboardInfoDTO(
                itemCount,
                lastPurchaseDate,
                itemsCloseToExpirationDateCount,
                possibleRecipes
        );
    }
}
