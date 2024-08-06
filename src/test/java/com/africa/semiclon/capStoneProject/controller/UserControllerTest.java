package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/data.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test1@gmail.com");
        request.setUsername("test1");
        request.setPhoneNumber("08163933470");
        request.setPassword("password");

        CreateUserResponse response = new CreateUserResponse();
        response.setMessage("Registration successful");
        response.setUserId(1L);

        when(userService.register(any(CreateUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", containsString("successful")))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void testUpdateUserProfile() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUserId(1L);
        request.setEmail("updated@gmail.com");
        request.setPhoneNumber("08063933470");
        request.setUsername("updatedTest");

        UpdateUserResponse response = new UpdateUserResponse();
        response.setMessage("Update successful");

        when(userService.updateProfile(any(UpdateUserRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString("successful")));
    }

    @Test
    void testSellWaste() throws Exception {
        SellWasteRequest request = new SellWasteRequest();
        request.setUserId(1L);
        request.setType(Category.PLASTIC);
        request.setQuantity("5");
        request.setWasteWeight(10);

        SellWasteResponse response = new SellWasteResponse();
        response.setMessage("Sell successful");

        when(userService.sellWaste(any(SellWasteRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/sellWaste")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString("successful")));
    }
}
