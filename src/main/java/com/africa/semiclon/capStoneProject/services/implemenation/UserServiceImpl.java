package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
        public CreateUserResponse register (CreateUserRequest createUserRequest){
            if (userRepository.existsByEmail(createUserRequest.getEmail())) {throw new UsernameExistsException("Username already exists: " + createUserRequest.getEmail());}
            User newUser = validateUserDetails(createUserRequest);
            newUser = userRepository.save(newUser);
            var response = modelMapper.map(newUser, CreateUserResponse.class);
            response.setMessage("user registered successfully");
            return response;
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
    public SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest) {
        Optional<User> optionalUser = userValidation(sellWasteRequest.getUserId());
        User user = optionalUser.get();
        Waste waste = getWasteDetails(sellWasteRequest);
        checkAndSetWasteForUsers(sellWasteRequest, user, waste);
        SellWasteResponse response = new SellWasteResponse();
        response.setMessage("Waste sold successfully");
        return response;
    }

    private void checkAndSetWasteForUsers(SellWasteRequest sellWasteRequest, User user, Waste waste) {
        List<Waste> wastes = user.getWastes();
        if (wastes == null) wastes = new ArrayList<>();
        wastes.add(waste);
        user.setWastes(wastes);
        user.setTotalWaste(user.getTotalWaste().add(BigDecimal.valueOf((sellWasteRequest.getWasteWeight()))));
        userRepository.save(user);
    }

    private Waste getWasteDetails(SellWasteRequest sellWasteRequest) {
        Waste waste = new Waste();
        waste.setUserId(sellWasteRequest.getUserId());
        waste.setType(sellWasteRequest.getType());
        waste.setQuantity(sellWasteRequest.getQuantity());
        wasteRepository.save(waste);
        return waste;
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
    public User getById(long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(

                        String.format("user with id %d not found", id)));

    }
    }

