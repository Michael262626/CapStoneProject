package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/data.sql")
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authenticateUserTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("john@email.com");
        request.setPassword("password");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/api/v1/auth")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void authenticateUserTest_FailsForInvalidCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("john@email.com");
        request.setPassword("wrongPassword");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/api/v1/auth")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}