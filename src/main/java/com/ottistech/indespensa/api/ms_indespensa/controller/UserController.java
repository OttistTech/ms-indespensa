package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.UserCredentialsResponse;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
