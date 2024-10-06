package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserCredentialsResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserCredentialsTokenResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpUserDTO signUpUserDTO) {

        UserCredentialsTokenResponseDTO userCredentialsResponse = userService.singUpUser(signUpUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCredentialsResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {

        UserCredentialsTokenResponseDTO userCredentials = userService.getUserCredentials(loginUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userCredentials);
    }

    @DeleteMapping("/deactivation/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable("id") Long userId) {

        userService.deactivateUserById(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserFullInfo(@PathVariable("id") Long userId,
                                             @RequestParam("full-info") boolean fullInfo) {

        if (fullInfo) {
            UserFullInfoResponseDTO userFullInfo = userService.getUserFullInfo(userId);
            return ResponseEntity.ok(userFullInfo);
        }

        UserCredentialsResponseDTO userHalfInfo = userService.getUserHalfInfo(userId);

        return ResponseEntity.ok(userHalfInfo);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserFullInfoResponseDTO>> getAllUsersFullInfo() {

        List<UserFullInfoResponseDTO> userFullInfoList = userService.getAllUsersFullInfo();

        return ResponseEntity.ok(userFullInfoList);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserFullInfoResponseDTO> getOneUserFullInfo(@PathVariable("id") Long userId) {

        UserFullInfoResponseDTO userFullInfo = userService.getUserFullInfo(userId);

        return ResponseEntity.ok(userFullInfo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId,
                                        @RequestBody @Valid UpdateUserDTO userDTO) {

        UserCredentialsResponseDTO userCredentials = userService.updateUser(userId, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userCredentials);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUserBecomePremium(@PathVariable("id") Long userId) {

        userService.updateUserBecomePremium(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
