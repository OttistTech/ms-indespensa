package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.EmailAlreadyInUseException;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User singUpUser(SignUpUserDTO signUpUserDTO) {
        if (userRepository.findByEmail(signUpUserDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = signUpUserDTO.toUser();

        return userRepository.save(user);
    }
}
