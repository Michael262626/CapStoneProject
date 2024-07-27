package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {



        private final UserRepository userRepository;
        private final ModelMapper modelMapper;
        private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
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
        Optional<User> optionalUser = userRepository.findById(updateUserRequest.getUserId());
        if (!optionalUser.isPresent()) {throw new UserNotFoundException(String.format("User with id %d not found", updateUserRequest.getUserId()));}
        User existingUser = validateUserInfo(updateUserRequest, optionalUser);
        User updatedUser = userRepository.save(existingUser);
        UpdateUserResponse response = modelMapper.map(updatedUser, UpdateUserResponse.class);
        response.setMessage("Profile updated successfully");
        return response;
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

