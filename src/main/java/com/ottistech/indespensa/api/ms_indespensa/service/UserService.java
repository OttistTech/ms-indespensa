package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.UserCredentialsResponse;
import com.ottistech.indespensa.api.ms_indespensa.exception.EmailAlreadyInUseException;
import com.ottistech.indespensa.api.ms_indespensa.exception.IncorrectPasswordException;
import com.ottistech.indespensa.api.ms_indespensa.exception.UserNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.AddressRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public User singUpUser(SignUpUserDTO signUpUserDTO) {
        if (userRepository.findByEmail(signUpUserDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = signUpUserDTO.toUser();
        user = userRepository.save(user);

        Address address = signUpUserDTO.toAddress(user);
        addressRepository.save(address);

        return user;
    }

    public UserCredentialsResponse getUserCredentials(LoginUserDTO loginUserDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginUserDTO.email());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();
        if(loginUserDTO.password().equals(user.getPassword())) {
            return new UserCredentialsResponse(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEnterpriseType(),
                user.getIsPremium()
            );
        } else {
            throw new IncorrectPasswordException("Password does not match");
        }
    }
}
