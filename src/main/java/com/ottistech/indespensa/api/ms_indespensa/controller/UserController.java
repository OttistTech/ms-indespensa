package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.*;
import com.ottistech.indespensa.api.ms_indespensa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpUserDTO signUpUserDTO) {

        UserCredentialsResponse userCredentialsResponse = userService.singUpUser(signUpUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCredentialsResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {

        UserCredentialsResponse userCredentials = userService.getUserCredentials(loginUserDTO);

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
            UserFullInfoResponse userFullInfo = userService.getUserFullInfo(userId);
            return ResponseEntity.ok(userFullInfo);
        }

        UserCredentialsResponse userHalfInfo = userService.getUserHalfInfo(userId);

        return ResponseEntity.ok(userHalfInfo);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserFullInfoResponse>> getAllUsersFullInfo() {

        List<UserFullInfoResponse> userFullInfoList = userService.getAllUsersFullInfo();

        return ResponseEntity.ok(userFullInfoList);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserFullInfoResponse> getOneUserFullInfo(@PathVariable("id") Long userId) {

        UserFullInfoResponse userFullInfo = userService.getUserFullInfo(userId);

        return ResponseEntity.ok(userFullInfo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpdateUserDTO userDTO) {

        UpdateUserResponseDTO updateUserDTO = userService.updateUser(userId, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updateUserDTO);
    }

}
