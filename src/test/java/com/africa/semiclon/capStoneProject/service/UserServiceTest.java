package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts= {"/db/data.sql"})
@Slf4j

public class UserServiceTest {

    @Autowired
    private UserService userService;

//    private CreateUserResponse response;
//
//    @BeforeEach
//    public void setUp() {
//
//    }
    @Test
    void registerUserTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setUsername("test");
        createUserRequest.setPhoneNumber("08163933470");
        createUserRequest.setPassword("password");
        CreateUserResponse response =
                userService.register(createUserRequest);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test
    void testThatUserCannotRegisterWithExistingEmail() {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setUsername("test");
        createUserRequest.setPhoneNumber("08163933470");
        createUserRequest.setPassword("password");
        var response = userService.register(createUserRequest);
        assertThat(response).isNotNull();
        assertThrows(UsernameExistsException.class, () -> {
            userService.register(createUserRequest);
        });
    }

    @Test
    void testThatUserCanUpdateProfile() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setUsername("test");
        createUserRequest.setPhoneNumber("08163933470");
        createUserRequest.setPassword("password");

        CreateUserResponse response = userService.register(createUserRequest);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));

        UpdateUserRequest updateUserRequest = getUpdateUserRequest(response);


        UpdateUserResponse updateUserResponse = userService.updateProfile(updateUserRequest);
        assertNotNull(updateUserResponse);
        assertTrue(updateUserResponse.getMessage().contains("success"));
    }

    private static UpdateUserRequest getUpdateUserRequest(CreateUserResponse response) {
        Long userId = response.getUserId(); // Assuming the response contains the user ID

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(userId); // Set the userId
        updateUserRequest.setEmail("fale@gmail.com");
        updateUserRequest.setPhoneNumber("08063933470"); // It might be better to use null for an empty value
        updateUserRequest.setUsername("test");
        updateUserRequest.setPassword("newPassword"); // It might be better to use null for an empty value
        return updateUserRequest;
    }

}
