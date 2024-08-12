//package com.africa.semiclon.capStoneProject.controller;
//
//import com.africa.semiclon.capStoneProject.data.models.User;
//import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
//import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
//import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
//import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
//import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
//import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
//import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {
//        CreateUserResponse response = userService.register(createUserRequest);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<UpdateUserResponse> updateUserProfile(@RequestBody UpdateUserRequest updateUserRequest) {
//        UpdateUserResponse response = userService.updateProfile(updateUserRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PostMapping("/sell-waste")
//    public ResponseEntity<SellWasteResponse> sellWaste(@RequestBody SellWasteRequest sellWasteRequest) {
//        SellWasteResponse response = userService.sellWaste(sellWasteRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable long id) {
//        User user = userService.getById(id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//}
