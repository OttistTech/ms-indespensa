package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.DashboardPersonalInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.DashboardProfileInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.CompletedRecipeRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.PantryItemRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ShopItemRepository;
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
    private final ShopItemRepository shopItemRepository;
    private final CompletedRecipeRepository completedRecipeRepository;

    public DashboardPersonalInfoDTO getPantryItemDashInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        Integer itemsInPantryCount = pantryItemRepository.countAllActiveItemsByUser(user);
        LocalDate lastPurchaseDate = pantryItemRepository.getLastPurchaseDate(user);
        int daysFromNow = 15;
        Integer itemsCloseToExpirationDateCount = pantryItemRepository.countAllItemsWithValidityWithinNextProvidedDays(user, LocalDate.now(), LocalDate.now().plusDays(daysFromNow));
        Integer possibleRecipes = pantryItemRepository.countAllPossibleRecipes(user.getUserId());

        return DashboardPersonalInfoDTO.fromAllDetails(itemsInPantryCount, lastPurchaseDate, itemsCloseToExpirationDateCount, possibleRecipes);
    }

    public DashboardProfileInfoDTO getProfileDashInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        Integer itemsInPantryCount = pantryItemRepository.countAllActiveItemsByUser(user);
        Integer purchasesMadeCount = shopItemRepository.countAllPurchaseHistoryItemsByUser(user);
        Integer productsAlreadyExpired = pantryItemRepository.countAllItemsAlreadyExpired(user, LocalDate.now());
        Integer recipesMadeCount = completedRecipeRepository.countAllCompletedRecipesByUser(user);

        return DashboardProfileInfoDTO.fromAllDetails(itemsInPantryCount, purchasesMadeCount, productsAlreadyExpired, recipesMadeCount);
    }
}
