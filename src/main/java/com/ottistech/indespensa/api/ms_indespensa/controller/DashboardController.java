package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.DashboardInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.DashboardProfileInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{user_id}/personal")
    public ResponseEntity<DashboardInfoDTO> getDashInfo(@PathVariable("user_id") Long userId) {

        DashboardInfoDTO dashboardInfoDTO = dashboardService.getPantryItemDashInfo(userId);

        return ResponseEntity.ok(dashboardInfoDTO);
    }

    @GetMapping("/{user_id}/profile")
    public ResponseEntity<?> getProfileInfo(@PathVariable("user_id") Long userId) {

        DashboardProfileInfoDTO dashboardProfileInfoDTO = dashboardService.getProfileDashInfo(userId);

        return ResponseEntity.ok(dashboardProfileInfoDTO);
    }
}
