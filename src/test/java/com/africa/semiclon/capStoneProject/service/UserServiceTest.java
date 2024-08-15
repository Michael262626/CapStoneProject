package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.EmailExistsException;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private CreateUserResponse createUserResponse;

    @BeforeEach
    void setup() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setUsername("test");
        createUserRequest.setPhoneNumber("08163933470");
        createUserRequest.setPassword("password");
        createUserResponse = userService.register(createUserRequest);
    }

    @Test
    void registerUserTest() {
        assertNotNull(createUserResponse);
        assertTrue(createUserResponse.getMessage().contains("success"));
    }

    @Test
    void testThatUserCannotRegisterWithExistingEmail() {
        CreateUserRequest duplicateEmailRequest = new CreateUserRequest();
        duplicateEmailRequest.setEmail("test@gmail.com");
        duplicateEmailRequest.setUsername("test");
        duplicateEmailRequest.setPhoneNumber("08163933470");
        duplicateEmailRequest.setPassword("password123");

        assertThrows(EmailExistsException.class, () -> userService.register(duplicateEmailRequest));

    }

    @Test
    void testThatUserCanUpdateProfile() {
        assertNotNull(createUserResponse);
        assertTrue(createUserResponse.getMessage().contains("success"));
        UpdateUserRequest updateUserRequest = getUpdateUserRequest(createUserResponse);
        UpdateUserResponse updateUserResponse = userService.updateProfile(updateUserRequest);
        assertNotNull(updateUserResponse);
        assertTrue(updateUserResponse.getMessage().contains("success"));
    }

    private static UpdateUserRequest getUpdateUserRequest(CreateUserResponse response) {
        Long userId = response.getUserId();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(userId);
        updateUserRequest.setEmail("fale@gmail.com");
        updateUserRequest.setPhoneNumber("08063933470");
        updateUserRequest.setUsername("updatedTest");
        return updateUserRequest;
    }

    @Test
    void testThatUserCannotUpdateProfileWithInvalidId() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(100L);
        updateUserRequest.setEmail("fale@gmail.com");
        updateUserRequest.setPhoneNumber("08063933470");
        updateUserRequest.setUsername("updatedTest");
        assertThrows(UserNotFoundException.class, () -> userService.updateProfile(updateUserRequest));
    }

    @Test
    void testThatUserCanSellWaste() {
        assertNotNull(createUserResponse);
        assertTrue(createUserResponse.getMessage().contains("success"));
        Long userId = createUserResponse.getUserId();
        SellWasteResponse sellWasteResponse = sellWasteDetails(userId);
        assertNotNull(sellWasteResponse);
        assertTrue(sellWasteResponse.getMessage().contains("success"));
    }

    private SellWasteResponse sellWasteDetails(Long userId) {
        SellWasteRequest sellWasteRequest = new SellWasteRequest();
//        sellWasteRequest.setUserId(userId);
        sellWasteRequest.setType(Category.PLASTIC);
        sellWasteRequest.setQuantity(5);
        return userService.sellWaste(sellWasteRequest);
    }

}
