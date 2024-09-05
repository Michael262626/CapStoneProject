package com.africa.semiclon.capStoneProject.services.implemenation;

import  com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.exception.*;
import com.africa.semiclon.capStoneProject.security.providers.CustomAuthenticationProvider;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final CustomAuthenticationProvider customUsernameProvider;

        private final UserRepository userRepository;
        private final WasteRepository wasteRepository;
        private final ModelMapper modelMapper;
        private final PasswordEncoder passwordEncoder;


        @Override
        public CreateUserResponse register (CreateUserRequest createUserRequest){
            validateEmptyString(createUserRequest);

            if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new EmailExistsException("Email already exists: " + createUserRequest.getEmail());}
            if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new UsernameExistsException("Username already exists: " + createUserRequest.getUsername());}
            if (userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists: " + createUserRequest.getPhoneNumber());}
            User newUser = validateUserDetails(createUserRequest);
            newUser = userRepository.save(newUser);
            var response = modelMapper.map(newUser, CreateUserResponse.class);
            response.setMessage("user registered successfully");
            return response;
        }

    private static void validateEmptyString(CreateUserRequest createUserRequest) {
        if (isEmptyOrNullString(createUserRequest.getEmail())) {throw new UserDetailsCannotBeNullOrEmpty("Email cannot be null or empty");}
        if (isEmptyOrNullString(createUserRequest.getUsername())) {throw new UserDetailsCannotBeNullOrEmpty("Username cannot be null or empty");}
        if (isEmptyOrNullString(createUserRequest.getPhoneNumber())) {throw new UserDetailsCannotBeNullOrEmpty("Email cannot be null or empty");}
        if (isEmptyOrNullString(createUserRequest.getPassword())) {throw new UserDetailsCannotBeNullOrEmpty("Email cannot be null or empty");}
    }

    public static boolean isEmptyOrNullString(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }


    private User validateUserDetails(CreateUserRequest createUserRequest) {
        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setAuthorities(new HashSet<>());
        var authorities = newUser.getAuthorities();
        authorities.add(Authority.USER);
        return newUser;
    }

    @Override
    public UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser = userValidation(updateUserRequest.getUserId());
        User existingUser = validateUserInfo(updateUserRequest, optionalUser);
        User updatedUser = userRepository.save(existingUser);
        UpdateUserResponse response = modelMapper.map(updatedUser, UpdateUserResponse.class);
        response.setMessage("Profile updated successfully");
        return response;
    }
    @Override
    public SearchResponse getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return modelMapper.map(user, SearchResponse.class);
    }

    @Override
    public SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest) {
        validateSellWasteRequest(sellWasteRequest);

        // Create waste entity based on the request
        Waste waste = createWasteEntity(sellWasteRequest);

        // Associate waste with the user and save
        // Accumulate the quantity of waste sold by the user
//        accumulateUserWaste(user, waste.getQuantity());

        // Prepare response
        SellWasteResponse response = new SellWasteResponse();
        response.setMessage("Waste sold successfully");

        return response;
    }

    // Helper method to accumulate the waste quantity
//    private void accumulateUserWaste(User user, int soldQuantity) {
//        // Assuming user has a field `totalWeightCollected` to track the accumulated waste
//        int currentTotal = user.getTotalWeightCollected();
//
//        user.setTotalWeightCollected(currentTotal + soldQuantity);
//
//        userRepository.save(user);
//    }
    @Override
    public WeightCollectedResponse getTotalWeightCollectedByUser(User user) {
        List<Waste> wasteList = user.getWastes();

        int totalWeight = wasteList.stream()
                .mapToInt(Waste::getQuantity)
                .sum();

        WeightCollectedResponse response = new WeightCollectedResponse();
        response.setWeight(totalWeight);
        return response;
    }
    private void validateSellWasteRequest(SellWasteRequest sellWasteRequest) {
        if (sellWasteRequest.getType() == null) {
            throw new IllegalArgumentException("Waste type is required.");
        }
    }

    private Waste createWasteEntity(SellWasteRequest sellWasteRequest) {
        Waste waste = new Waste();
        waste.setType(sellWasteRequest.getType());
        waste.setQuantity(sellWasteRequest.getQuantity());
        return wasteRepository.save(waste);
    }



    private Optional<User> userValidation(Long sellWasteRequest) {
        Optional<User> optionalUser = userRepository.findById(sellWasteRequest);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id %d not found", sellWasteRequest));
        }
        return optionalUser;
    }


    private static User validateUserInfo(UpdateUserRequest updateUserRequest, Optional<User> optionalUser) {
        User existingUser = optionalUser.get();
        if (updateUserRequest.getUsername() != null) {existingUser.setUsername(updateUserRequest.getUsername());}
        if (updateUserRequest.getEmail() != null) {existingUser.setEmail(updateUserRequest.getEmail());}
        if (updateUserRequest.getPhoneNumber() != null) {existingUser.setPhoneNumber(updateUserRequest.getPhoneNumber());}
        return existingUser;
    }


    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(
                        String.format("user with id %d not found", id)));

    }
    }

