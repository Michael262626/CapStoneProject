package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.WeightCollectedResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import com.africa.semiclon.capStoneProject.services.interfaces.WasteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse response = userService.register(createUserRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse response = userService.updateProfile(updateUserRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sellWaste")
    public ResponseEntity<SellWasteResponse> sellWaste(@RequestBody SellWasteRequest sellWasteRequest) {
        log.info("reached here: ");
        SellWasteResponse response = userService.sellWaste(sellWasteRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/totalWeight/{userId}")
    public ResponseEntity<?> getTotalWeightCollectedByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        WeightCollectedResponse response = userService.getTotalWeightCollectedByUser(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
