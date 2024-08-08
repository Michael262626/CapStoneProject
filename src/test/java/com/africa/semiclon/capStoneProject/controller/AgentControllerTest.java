package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateAgentProfileRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ApiResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateAgentProfileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AgentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerAgentTest() throws Exception {
        RegisterAgentRequest request = new RegisterAgentRequest();
        request.setPhoneNumber("09012345678");
        request.setUsername("dark_royal");
        request.setEmail("dark@gmail.com");
        request.setPassword("MyPassword123@");
        byte[] content = new ObjectMapper().writeValueAsBytes(request);
        mockMvc.perform(post("/api/v1/agent/signup")
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void updateAgentProfile() throws Exception {
        UpdateAgentProfileRequest request = new UpdateAgentProfileRequest();
        Address address = new Address();
        address.setCity("Lagos");
        address.setZipCode("1234");
        address.setPostalCode("54321");
        address.setStreetName("igberen");
        request.setAddress(address);
        request.setEmail("real@gmail.com");
        byte[] content = new ObjectMapper().writeValueAsBytes(request);
        mockMvc.perform(post("/api/v1/agent/updateProfile")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
