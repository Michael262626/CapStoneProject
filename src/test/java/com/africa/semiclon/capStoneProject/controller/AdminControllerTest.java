package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.LoginRequest;
import com.africa.semiclon.capStoneProject.dtos.request.ManageUsersRequest;
import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ManageUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void manageUsersTest() throws Exception {
        ManageUsersRequest request = new ManageUsersRequest();
        request.setAdminId(1L);
        ManageUserResponse response = new ManageUserResponse();
        Mockito.when(adminService.manageUsers(Mockito.any(ManageUsersRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/manageUsers")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"1\"}"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}
