package com.africa.semiclon.capStoneProject.services;

import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void registerTest(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test12@gmail.com");
        createUserRequest.setPassword("password");

        CreateUserResponse response = userService.register(createUserRequest);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));

    }


}
