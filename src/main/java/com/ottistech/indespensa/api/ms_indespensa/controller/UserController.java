package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.UserCredentialsResponse;
import com.ottistech.indespensa.api.ms_indespensa.dto.UserFullInfoResponse;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpUserDTO signUpUserDTO) {

        User user = userService.singUpUser(signUpUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
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
    public ResponseEntity<?> getFullInfoUser(@PathVariable("id") Long userId,
                                             @RequestParam("full-info") boolean fullInfo) {

        if (fullInfo) {
            UserFullInfoResponse userFullInfo = userService.getFullInfoUser(userId);
            return ResponseEntity.ok(userFullInfo);
        }

        UserCredentialsResponse userHalfInfo = userService.getHalfInfoUser(userId);
        return ResponseEntity.ok(userHalfInfo);

    }
}
