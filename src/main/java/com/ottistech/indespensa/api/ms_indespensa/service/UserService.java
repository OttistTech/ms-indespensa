package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserCredentialsResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.*;
import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.AddressRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.CepRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CepRepository cepRepository;
    private final CepService cepService;

    public UserCredentialsResponseDTO singUpUser(SignUpUserDTO signUpUserDTO) {
        if (userRepository.findByEmail(signUpUserDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = signUpUserDTO.toUser();
        user = userRepository.save(user);

        Cep cep = cepService.getOrCreateCep(signUpUserDTO.toCep());

        Address address = signUpUserDTO.toAddress(user, cep);
        addressRepository.save(address);

        return UserCredentialsResponseDTO.fromUser(user);
    }

    public UserCredentialsResponseDTO getUserCredentials(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        if (user.getDeactivatedAt() != null) {
            throw new UserAlreadyDeactivatedException("User already deactivated");
        } else if(loginUserDTO.password().equals(user.getPassword())) {
            return UserCredentialsResponseDTO.fromUser(user);
        } else {
            throw new IncorrectPasswordException("Password does not match");
        }
    }

    public void deactivateUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        if (user.getDeactivatedAt() != null) {
            throw new UserAlreadyDeactivatedException("User already deactivated");
        }

        user.setDeactivatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public UserFullInfoResponseDTO getUserFullInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Address address = addressRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found for this user"));

        Cep cep = cepRepository.findById(address.getCep().getCepId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cep not found for this user"));

        return UserFullInfoResponseDTO.fromUserCepAddress(user, cep, address);
    }

    public UserCredentialsResponseDTO getUserHalfInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        return UserCredentialsResponseDTO.fromUser(user);
    }

    public List<UserFullInfoResponseDTO> getAllUsersFullInfo() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }

        // TODO: verify if we can do the repository return the dto
        List<UserFullInfoResponseDTO> userFullInfoResponses = new ArrayList<>();

        for (User user : users) {

            Address address = addressRepository.findByUserId(user.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found for user with ID: " + user.getUserId()));

            Cep cep = cepRepository.findById(address.getCep().getCepId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cep not found for this user"));

            UserFullInfoResponseDTO userFullInfo = UserFullInfoResponseDTO.fromUserCepAddress(user, cep, address);

            userFullInfoResponses.add(userFullInfo);
        }

        return userFullInfoResponses;
    }

    public UserCredentialsResponseDTO updateUser(Long userId, UpdateUserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.findByEmail(userDTO.email())
                .filter(existingUser -> !existingUser.getUserId().equals(userId))
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyInUseException("Email is already in use by another user");
                });

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        Address address = addressRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found for this user"));

        address.setAddressNumber(userDTO.addressNumber());

        Cep cep = cepRepository.findById(userDTO.cep())
                .orElseGet(() -> {
                    Cep newCep = userDTO.toCep();
                    cepRepository.save(newCep);

                    return newCep;
                });

        address.setCep(cep);
        userRepository.save(user);
        addressRepository.save(address);

        return UserCredentialsResponseDTO.fromUser(user);
    }
}
