package com.africa.semiclon.capStoneProject.security.services.Implementation;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ApiResponse;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.security.model.BlacklistedToken;
import com.africa.semiclon.capStoneProject.security.repository.BlacklistedTokenRepository;
import com.africa.semiclon.capStoneProject.security.services.interfaces.AuthServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthServices {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest request) {
        String username = request.getUsername().toLowerCase();
        validate(username);
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        User newUser = registerNewUser(request);
        RegisterResponse registerResponse = mapper.map(newUser, RegisterResponse.class);
        registerResponse.setMessage("User registered successfully");

        return new ApiResponse<>(now(), true, registerResponse);
    }

    @Override
    public void blacklist(String token) {
        log.info("Trying to blacklist token: {}", token);
        trackExpiredTokens();
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiresAt(Instant.now().plus(1, HOURS));
        blacklistedTokenRepository.save(blacklistedToken);
        log.info("Blacklisted token: {}", token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        log.info("Checking blacklist status of token: {}", token);
        boolean isBlacklisted = blacklistedTokenRepository.existsByToken(token);
        log.info("Blacklist status of token: {}", isBlacklisted);
        trackExpiredTokens();
        return isBlacklisted;
    }

    private void trackExpiredTokens() {
        log.info("Tracking and deleting expired user tokens");
        var blacklist = blacklistedTokenRepository.findAll();
        blacklist.stream()
                .filter(blacklistedToken -> Instant.now().isAfter(blacklistedToken.getExpiresAt()))
                .forEach(blacklistedTokenRepository::delete);
        log.info("Expired user tokens successfully tracked and deleted");
    }

    private User registerNewUser(RegisterRequest request) {
        User newUser = mapper.map(request, User.class);
        return userRepository.save(newUser);
    }

    private void validate(String username) {
        boolean usernameExists = userRepository.existsByUsername(username);
        if (usernameExists) {
            log.error("Username '{}' is already taken", username);
            throw new UsernameExistsException("Username is already taken");
        }
    }
}
