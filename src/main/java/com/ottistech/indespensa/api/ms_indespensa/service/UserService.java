package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.*;
import com.ottistech.indespensa.api.ms_indespensa.exception.*;
import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.AddressRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.CepRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CepRepository cepRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, CepRepository cepRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.cepRepository = cepRepository;
    }

    public UserCredentialsResponseDTO singUpUser(SignUpUserDTO signUpUserDTO) {
        if (userRepository.findByEmail(signUpUserDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = signUpUserDTO.toUser();
        user = userRepository.save(user);

        Cep cep = cepRepository.findById(signUpUserDTO.cep())
                .orElseGet(() -> {
                    Cep newCep = signUpUserDTO.toCep();
                    return cepRepository.save(newCep);
                });

        Address address = signUpUserDTO.toAddress(user, cep);
        addressRepository.save(address);

        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getEnterpriseType(),
                user.getIsPremium()
        );
    }

    public UserCredentialsResponseDTO getUserCredentials(LoginUserDTO loginUserDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginUserDTO.email());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();

        if (user.getDeactivatedAt() != null) {
            throw new UserAlreadyDeactivatedException("User already deactivated");
        } else if(loginUserDTO.password().equals(user.getPassword())) {
            return new UserCredentialsResponseDTO(
                    user.getUserId(),
                    user.getType(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getEnterpriseType(),
                    user.getIsPremium()
            );
        } else {
            throw new IncorrectPasswordException("Password does not match");
        }
    }

    public void deactivateUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();

        if (user.getDeactivatedAt() != null) {
            throw new UserAlreadyDeactivatedException("User already deactivated");
        }

        user.setDeactivatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public UserFullInfoResponseDTO getUserFullInfo(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();

        Optional<Address> addressOptional = addressRepository.findByUserId(user.getUserId());

        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException("Address not found for this user");
        }

        Address address = addressOptional.get();

        Optional<Cep> cepOptional = cepRepository.findById(address.getCep().getCepId());

        if (cepOptional.isEmpty()) {
            throw new CepNotFoundException("Cep not found for this user");
        }

        Cep cep = cepOptional.get();

        return new UserFullInfoResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getBirthDate(),
                user.getEnterpriseType(),
                user.getEmail(),
                user.getPassword(),
                cep.getCepId(),
                address.getAddressNumber(),
                cep.getStreet(),
                cep.getCity(),
                cep.getState(),
                user.getIsPremium()
        );
    }

    public UserCredentialsResponseDTO getUserHalfInfo(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        User user = userOptional.get();

        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getEnterpriseType(),
                user.getIsPremium()
        );
    }

    public List<UserFullInfoResponseDTO> getAllUsersFullInfo() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }

        List<UserFullInfoResponseDTO> userFullInfoResponses = new ArrayList<>();

        for (User user : users) {
            Optional<Address> addressOptional = addressRepository.findByUserId(user.getUserId());

            if (addressOptional.isEmpty()) {
                // talvez não precise implicitar o userId que não foi encontrado
                throw new AddressNotFoundException("Address not found for user with ID: " + user.getUserId());
            }

            Address address = addressOptional.get();

            Optional<Cep> optionalCep = cepRepository.findById(address.getCep().getCepId());

            if (optionalCep.isEmpty()) {
                throw new CepNotFoundException("Cep not found for this user");
            }

            Cep cep = optionalCep.get();

            UserFullInfoResponseDTO userFullInfo = new UserFullInfoResponseDTO(
                    user.getUserId(),
                    user.getType(),
                    user.getName(),
                    user.getBirthDate(),
                    user.getEnterpriseType(),
                    user.getEmail(),
                    user.getPassword(),
                    cep.getCepId(),
                    address.getAddressNumber(),
                    cep.getStreet(),
                    cep.getCity(),
                    cep.getState(),
                    user.getIsPremium()
            );

            userFullInfoResponses.add(userFullInfo);
        }

        return userFullInfoResponses;
    }

    public UserCredentialsResponseDTO updateUser(Long userId, UpdateUserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.findByEmail(userDTO.email())
                .filter(existingUser -> !existingUser.getUserId().equals(userId))
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyInUseException("Email is already in use by another user");
                });

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        Address address = addressRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new AddressNotFoundException("Address not found for this user"));

        address.setAddressNumber(userDTO.addressNumber());

        Cep cep = cepRepository.findById(userDTO.cep())
                .orElseGet(() -> {
                    Cep newCep = new Cep();
                    newCep.setCepId(userDTO.cep());
                    newCep.setStreet(userDTO.street());
                    newCep.setCity(userDTO.city());
                    newCep.setState(userDTO.state());
                    cepRepository.save(newCep);

                    return newCep;
                });

        address.setCep(cep);
        userRepository.save(user);
        addressRepository.save(address);

        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnterpriseType(),
                user.getIsPremium()
        );
    }
}
