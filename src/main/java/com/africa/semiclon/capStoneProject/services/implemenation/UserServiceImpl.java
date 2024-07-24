package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        newUser.setAuthorities(new HashSet<>());
        var authorities = newUser.getAuthorities();
        authorities.add(Authority.USER);
        newUser = userRepository.save(newUser);
        var response = modelMapper.map(newUser, CreateUserResponse.class);
        response.setMessage("user registered successfully");
        return response;
    }
}
