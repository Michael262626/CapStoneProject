package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        ManageUserResponse response = new ManageUserResponse();
        Mockito.when(adminService.manageUsers(Mockito.any(ManageUsersRequest.class)))
                .thenReturn(response);
        mockMvc.perform(get("/api/v1/admin/manageUsers")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void ViewAllWasteTest() throws Exception {
        ViewWasteResponse response = new ViewWasteResponse();
        Mockito.when(adminService.viewAllWaste(Mockito.any(ViewWasteRequest.class)))
                .thenReturn(response);
        mockMvc.perform(get("/api/v1/admin/viewAllWaste")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void assignWasteToAgent() throws Exception {
        AssignWasteResponse response = new AssignWasteResponse();
        Mockito.when(adminService.assignWasteToAgent(Mockito.any(AssignWasteRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/assignWasteToAgent")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void generateWasteReport() throws Exception {
        WasteReportResponse response = new WasteReportResponse();
        Mockito.when(adminService.generateWasteReport(Mockito.any(GenerateWasteReportRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/generateWasteReport")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void sendNotification() throws Exception {
        NotificationResponse response = new NotificationResponse();
        Mockito.when(adminService.sendNotificationRequest(Mockito.any(NotificationRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/generateWasteReport")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteUser() throws Exception {
        DeleteUserResponse response = new DeleteUserResponse();
        Mockito.when(adminService.deleteUser(Mockito.any(DeleteUserRequest.class)))
                .thenReturn(response);
        mockMvc.perform(delete("/api/v1/admin/deleteUser")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void registerAgent() throws Exception {
        RegisterAgentResponse response = new RegisterAgentResponse();
        Mockito.when(adminService.registerAgent(Mockito.any(RegisterAgentRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/registerAgent")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void registerWaste() throws Exception {
        RegisterWasteResponse response = new RegisterWasteResponse();
        Mockito.when(adminService.registerWasteForSale(Mockito.any(RegisterWasteRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/admin/registerWasteForSale")
                        .contentType(APPLICATION_JSON)
                        .content("{\"adminId\": \"300\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

    }


}
