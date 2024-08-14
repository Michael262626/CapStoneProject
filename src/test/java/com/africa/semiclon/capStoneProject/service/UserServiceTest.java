package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.exception.*;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
        createUserRequest.setPassword("@Twi1234");
        createUserResponse = userService.register(createUserRequest);
    }

    @Test
    void registerUserTest() {
        assertNotNull(createUserResponse);
        assertTrue(createUserResponse.getMessage().contains("User registered successfully"));
    }

    @Test
    void testThatUserCannotRegisterWithExistingEmail_PhoneNumber_Username() {
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
        assertTrue(createUserResponse.getMessage().contains("User registered successfully"));

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(createUserResponse.getUserId());
        updateUserRequest.setEmail("updated@gmail.com");
        updateUserRequest.setPhoneNumber("08063933470");
        updateUserRequest.setUsername("updatedTest");

        UpdateUserResponse updateUserResponse = userService.updateProfile(updateUserRequest);
        assertNotNull(updateUserResponse);
        assertTrue(updateUserResponse.getMessage().contains("Profile updated successfully"));
    }

//    @Test
//    void testThatUserCannotUpdateProfileWithInvalidId() {
//        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
//        updateUserRequest.setUserId(100L);
//        updateUserRequest.setEmail("invalid@gmail.com");
//        updateUserRequest.setPhoneNumber("08063933470");
//        updateUserRequest.setUsername("invalidTest");
//
//        assertThrows(UserNotFoundException.class, () -> userService.updateProfile(updateUserRequest));
//    }

    @Test
    void testThatUserCanSellWaste() {
        assertNotNull(createUserResponse);
        assertTrue(createUserResponse.getMessage().contains("User registered successfully"));

        SellWasteRequest sellWasteRequest = new SellWasteRequest();
        sellWasteRequest.setUserId(createUserResponse.getUserId());
        sellWasteRequest.setType(Category.PLASTIC);
        sellWasteRequest.setQuantity("5kg");

        SellWasteResponse sellWasteResponse = userService.sellWaste(sellWasteRequest);
        assertNotNull(sellWasteResponse);
        assertTrue(sellWasteResponse.getMessage().contains("Waste sold successfully"));
    }



//    @Test
//    void testThatUserCannotRegisterWithInvalidEmail() {
//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setEmail("invalid-email");
//        createUserRequest.setUsername("testUser2");
//        createUserRequest.setPhoneNumber("08163933471");
//        createUserRequest.setPassword("ValidPass@123");
//
//        assertThrows(InvalidEmailException.class, () -> userService.register(createUserRequest));
//    }

    @Test
    void testThatUserCannotRegisterWithInvalidPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("newtest@gmail.com");
        createUserRequest.setUsername("testUser3");
        createUserRequest.setPhoneNumber("08163933472");
        createUserRequest.setPassword("short");

        assertThrows(InvalidPasswordException.class, () -> userService.register(createUserRequest));
    }

    @Test
    void testThatUserCannotSellWasteWithInvalidUserId() {
        SellWasteRequest sellWasteRequest = new SellWasteRequest();
        sellWasteRequest.setUserId(999L);
        sellWasteRequest.setType(Category.PLASTIC);
        sellWasteRequest.setQuantity("10kg");

        assertThrows(UserNotFoundException.class, () -> userService.sellWaste(sellWasteRequest));
    }
//
//    @Test
//    void testThatUserCannotUpdateWithDuplicateEmail() {
//
//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setEmail("newuser@gmail.com");
//        createUserRequest.setUsername("newuser");
//        createUserRequest.setPhoneNumber("08163933473");
//        createUserRequest.setPassword("ValidPass@123");
//        userService.register(createUserRequest);
//
//
//        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
//        updateUserRequest.setUserId(createUserResponse.getUserId());
//        updateUserRequest.setEmail("newuser@gmail.com");
//
//        assertThrows(EmailExistsException.class, () -> userService.updateProfile(updateUserRequest));
//    }

    @Test
    void testThatUserCanRetrieveById() {
        User user = userService.getById(createUserResponse.getUserId());
        assertNotNull(user);
        assertEquals(createUserResponse.getUserId(), user.getUserId());
    }

    @Test
    void testThatRetrievingNonExistentUserByIdThrowsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getById(999L));
    }

//    @Test
//    void testThatUserCannotUpdateWithInvalidEmail() {
//        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
//        updateUserRequest.setUserId(createUserResponse.getUserId());
//        updateUserRequest.setEmail("invalid-email");
//
//        assertThrows(InvalidEmailException.class, () -> userService.updateProfile(updateUserRequest));
//    }

    @Test
    void testThatUserCannotUpdateWithInvalidPhoneNumber() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(createUserResponse.getUserId());
        updateUserRequest.setPhoneNumber("12345");

        assertThrows(InvalidPhoneNumberException.class, () -> userService.updateProfile(updateUserRequest));
    }

//    @Test
//    void testThatAllUsersCanBeRetrieved() {
//        List<User> users = userService.getAllUsers();
//        assertNotNull(users);
//        assertFalse(users.isEmpty());
//    }
}
