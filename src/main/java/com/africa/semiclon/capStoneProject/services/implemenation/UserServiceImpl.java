package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.*;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WasteRepository wasteRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, WasteRepository wasteRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.wasteRepository = wasteRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        validateRequest(createUserRequest);
        checkIfUserExists(createUserRequest);
        User newUser = mapToUser(createUserRequest);
        newUser = userRepository.save(newUser);
        return buildCreateUserResponse(newUser);
    }

    private void checkIfUserExists(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new EmailExistsException("Email already exists: " + createUserRequest.getEmail());
        }
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new UsernameExistsException("Username already exists: " + createUserRequest.getUsername());
        }
        if (userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists: " + createUserRequest.getPhoneNumber());
        }
    }

    private static void validateRequest(CreateUserRequest createUserRequest) {
        if (isEmptyOrNull(createUserRequest.getEmail())) {
            throw new UserDetailsCannotBeNullOrEmpty("Email cannot be null or empty");
        }
        if (isEmptyOrNull(createUserRequest.getUsername())) {
            throw new UserDetailsCannotBeNullOrEmpty("Username cannot be null or empty");
        }
        if (isEmptyOrNull(createUserRequest.getPhoneNumber())) {
            throw new UserDetailsCannotBeNullOrEmpty("Phone number cannot be null or empty");
        }
        if (isEmptyOrNull(createUserRequest.getPassword())) {
            throw new UserDetailsCannotBeNullOrEmpty("Password cannot be null or empty");
        }
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isBlank();
    }

    private User mapToUser(CreateUserRequest createUserRequest) {
        validatePasswordAndEmail(createUserRequest);
        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setAuthorities(new HashSet<>());
        newUser.getAuthorities().add(Authority.USER);
        return newUser;
    }

    private void validatePasswordAndEmail(CreateUserRequest createUserRequest) {
        if (!isValidPassword(createUserRequest.getPassword())) {
            throw new InvalidPasswordException("Password does not meet the required criteria");
        }
        if (!isEmailValid(createUserRequest.getEmail())) {
            throw new InvalidEmailException("Invalid email format");
        }
        if (!isPhoneNumberValid(createUserRequest.getPhoneNumber())) {
            throw new InvalidPhoneNumberException("Invalid phone number format");
        }
    }

    private CreateUserResponse buildCreateUserResponse(User user) {
        CreateUserResponse response = modelMapper.map(user, CreateUserResponse.class);
        response.setMessage("User registered successfully");
        return response;
    }

    @Override
    public UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest) {
        User existingUser = validateUserAndUpdateInfo(updateUserRequest);
        User updatedUser = userRepository.save(existingUser);
        return buildUpdateUserResponse(updatedUser);
    }

    private User validateUserAndUpdateInfo(UpdateUserRequest updateUserRequest) {
        User existingUser = userRepository.findById(updateUserRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        updateUserFields(updateUserRequest, existingUser);
        return existingUser;
    }

    private void updateUserFields(UpdateUserRequest updateUserRequest, User existingUser) {
        if (updateUserRequest.getUsername() != null) {
            existingUser.setUsername(updateUserRequest.getUsername());
        }
        if (updateUserRequest.getEmail() != null) {
            if (!isEmailValid(updateUserRequest.getEmail())) {
                throw new InvalidEmailException("Invalid email format");
            }
            existingUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPhoneNumber() != null) {
            if (!isPhoneNumberValid(updateUserRequest.getPhoneNumber())) {
                throw new InvalidPhoneNumberException("Invalid phone number format");
            }
            existingUser.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
    }

    private UpdateUserResponse buildUpdateUserResponse(User updatedUser) {
        UpdateUserResponse response = modelMapper.map(updatedUser, UpdateUserResponse.class);
        response.setMessage("Profile updated successfully");
        return response;
    }

    @Override
    public SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest) {
        User user = validateUser(sellWasteRequest.getUserId());
        Waste waste = buildWaste(sellWasteRequest);
        assignWasteToUser(user, waste);
        return buildSellWasteResponse();
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", userId)));
    }

    private Waste buildWaste(SellWasteRequest sellWasteRequest) {
        Waste waste = new Waste();
        waste.setUserId(sellWasteRequest.getUserId());
        waste.setType(sellWasteRequest.getType());
        waste.setQuantity(sellWasteRequest.getQuantity());
        wasteRepository.save(waste);
        return waste;
    }

    private void assignWasteToUser(User user, Waste waste) {
        wasteRepository.findById(user.getUserId());
        String wasteQuantity = waste.getQuantity();
        waste.setQuantity(wasteQuantity == null ? "" : wasteQuantity);
        userRepository.save(user);
    }

    private SellWasteResponse buildSellWasteResponse() {
        SellWasteResponse response = new SellWasteResponse();
        response.setMessage("Waste sold successfully");
        return response;
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("^(?:\\+?234)?(0[789]\\d{9})$");
    }
}
