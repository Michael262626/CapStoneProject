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


    public UserServiceImpl(UserRepository userRepository,
                           WasteRepository wasteRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.wasteRepository = wasteRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        validateCreateUserRequest(createUserRequest);
        checkIfUserExists(createUserRequest);
        User newUser = mapAndPrepareUser(createUserRequest);
        newUser = userRepository.save(newUser);
        CreateUserResponse response = modelMapper.map(newUser, CreateUserResponse.class);
        response.setMessage("User registered successfully");
        return response;
    }

    private void validateCreateUserRequest(CreateUserRequest createUserRequest) {
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

    private User mapAndPrepareUser(CreateUserRequest createUserRequest) {
        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(validatePassword(createUserRequest.getPassword())));
        newUser.setEmail(validateEmail(createUserRequest.getEmail()));
        newUser.setPhoneNumber(validatePhoneNumber(createUserRequest.getPhoneNumber()));
        newUser.setAuthorities(new HashSet<>());
        newUser.getAuthorities().add(Authority.USER);
        return newUser;
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    public static String validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserDetailsCannotBeNullOrEmpty("Invalid email format");
        }
        return email;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^(\\+234|234|0)(70[1-9]|80[2-9]|81[0-9]|90[1-9])\\d{7}$")) {
            throw new UserDetailsCannotBeNullOrEmpty("Invalid phone number format");
        }
        return phoneNumber;
    }


    public static String validatePassword(String password) {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new UserDetailsCannotBeNullOrEmpty("Invalid password format");
        }
        return password;
    }



    @Override
    public UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest) {
        User existingUser = getUserById(updateUserRequest.getUserId());
        updateExistingUser(existingUser, updateUserRequest);
        User updatedUser = userRepository.save(existingUser);
        UpdateUserResponse response = modelMapper.map(updatedUser, UpdateUserResponse.class);
        response.setMessage("Profile updated successfully");
        return response;
    }

    private void updateExistingUser(User existingUser, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getUsername() != null) {
            existingUser.setUsername(updateUserRequest.getUsername());
        }
        if (updateUserRequest.getEmail() != null) {
            existingUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
    }

    @Override
    public SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest) {
        User user = getUserById(sellWasteRequest.getUserId());
        Waste waste = mapAndSaveWaste(sellWasteRequest);

        updateUserWaste(user, waste);

        SellWasteResponse response = new SellWasteResponse();
        response.setMessage("Waste sold successfully");
        return response;
    }

    private void updateUserWaste(User user, Waste waste) {
        wasteRepository.findById(user.getUserId());
        user.getWastes().add(waste);
        userRepository.save(user);
    }

    private Waste mapAndSaveWaste(SellWasteRequest sellWasteRequest) {
        Waste waste = new Waste();
        waste.setUserId(sellWasteRequest.getUserId());
        waste.setType(sellWasteRequest.getType());
        waste.setQuantity(sellWasteRequest.getQuantity());
        return wasteRepository.save(waste);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", userId)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
    }

}
