package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts= {"/db/data.sql"})
@Slf4j

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void registerUserTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setPassword("password");
        CreateUserResponse response = userService.register(createUserRequest);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test

    void registerUserWithExistingUsernameTest() {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setPassword("password");
        var response = userService.register(createUserRequest);
        assertThat(response).isNotNull();
        assertThrows(UsernameExistsException.class, () -> {
            userService.register(createUserRequest);
        });


    }
}
