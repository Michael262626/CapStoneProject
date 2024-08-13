package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            CreateUserResponse response = userService.register(createUserRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            UpdateUserResponse response = userService.updateProfile(updateUserRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/sellWaste")
    public ResponseEntity<?> sellWaste(@RequestBody SellWasteRequest sellWasteRequest) {
        try {
            SellWasteResponse response = userService.sellWaste(sellWasteRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            User user = userService.getById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}


