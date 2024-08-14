package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.*;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private WasteRepository wasteRepository;


    @Test
    public void testAdminCanManageMultipleUsers() {
        User user1 = new User();
        user1.setUsername("User1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("User2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("password2");
        userRepository.save(user2);

        ManageUsersRequest request = new ManageUsersRequest();
        request.setAdminId(1L);

        ManageUserResponse response = adminService.manageUsers(request);
        assertThat(response).isNotNull();
        assertThat(response.getUsers()).isNotEmpty();
        assertThat(response.getUsers().size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testAdminCanViewWastes(){
        ViewWasteRequest viewWasteRequest = new ViewWasteRequest();
        viewWasteRequest.setAdminId(1L);
        ViewWasteResponse response = adminService.viewAllWaste(viewWasteRequest);
        assertThat(response).isNotNull();
        assertThat(response.getWastes()).isNotEmpty();
        assertThat(response.getWastes().size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    public void testToAssignWasteToAgent(){
        AssignWasteRequest request = new AssignWasteRequest();
        request.setAgentId(2L);
        request.setWasteId(400L);
        AssignWasteResponse response = adminService.assignWasteToAgent(request);
        assertThat(response).isNotNull();
        assertThat(response.getWasteId()).isEqualTo(400L);
        assertThat(response.getAgentId()).isEqualTo(2L);
        assertThat(response.getMessage()).contains("Successfully assigned");

    }

//    @Test
//    public void testGenerateWasteReport() {
//        Agent agent = new Agent();
//        agent.setAgentId(2L);
//        agent.setUsername("Agent1");
//
//        Waste waste1 = new Waste();
//        waste1.setWasteId(1L);
//        waste1.setType(PLASTIC);
//        waste1.setQuantity("10kg");
//        waste1.setPrice(BigDecimal.valueOf(100));
//        waste1.setWasteCollectionDate(LocalDateTime.now().minusDays(2));
//        waste1.setAgent(agent);
//        wasteRepository.save(waste1);
//
//        GenerateWasteReportRequest request = new GenerateWasteReportRequest();
//        request.setStartDate(LocalDateTime.now().minusDays(5));
//        request.setEndDate(LocalDateTime.now());
//
//        WasteReportResponse response = adminService.generateWasteReport(request);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getReportItems()).isNotEmpty();
//        assertThat(response.getMessage()).isEqualTo("Report generated successfully");
//
//
//        WasteReport reportItem = response.getReportItems().get(0);
//        assertThat(reportItem.getQuantity()).isEqualTo("10kg");
//        assertThat(reportItem.getAssignedAgent()).isEqualTo("Agent1");
//    }


    @Test
    public void testAdminCanSendNotificationsToUsers(){
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTitle("Pollution Alert");
        notificationRequest.setContent("Check the pollution levels in your area.");
        notificationRequest.setRecipientEmail("ayomidebanjo02@gmail.com");

        NotificationResponse response = adminService.sendNotificationRequest(notificationRequest);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Notification sent successfully");


    }

    @Test
    public void testAdminCanDeleteUser(){
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        userRepository.save(user);
        User savedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(savedUser).isNotNull();
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setUserId(user.getUserId());

        adminService.deleteUser(deleteRequest);
        User deletedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(deletedUser).isNull();
    }

    @Test
    public void testAdminCanRegisterAnAgent(){
        RegisterAgentRequest registerRequest = new RegisterAgentRequest();
        registerRequest.setUsername("agent001");
        registerRequest.setEmail("agent001@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("AgentOne");
        registerRequest.setPhoneNumber("08034589034");
        RegisterAgentResponse response = adminService.registerAgent(registerRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Agent registered successfully");
        assertThat(response.getAgentId()).isNotNull();
        Agent savedAgent = agentRepository.findById(response.getAgentId()).orElse(null);
        assertThat(savedAgent).isNotNull();
        assertThat(savedAgent.getUsername()).isEqualTo("agent001");
        assertThat(savedAgent.getEmail()).isEqualTo("agent001@example.com");

    }

    @Test
    public void AdminCanRegisterWasteForSale(){
        RegisterWasteRequest registerWasteRequest = new RegisterWasteRequest();
        registerWasteRequest.setType(Category.PLASTIC);
        registerWasteRequest.setQuantity("9kg");
        registerWasteRequest.setPrice(BigDecimal.valueOf(200.00));
        registerWasteRequest.setDescription("High-quality recycled plastic");
        registerWasteRequest.setAgentId(2L);
        RegisterWasteResponse response = adminService.registerWasteForSale(registerWasteRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Waste registered successfully for sale");
        assertThat(response.getWasteId()).isNotNull();
        Waste savedWaste = wasteRepository.findById(response.getWasteId()).orElse(null);
        assertThat(savedWaste).isNotNull();
        assertThat(savedWaste.getType()).isEqualTo(Category.PLASTIC);
        assertThat(savedWaste.getQuantity()).isEqualTo("9kg");
        assertThat(savedWaste.getPrice()).isEqualTo(BigDecimal.valueOf(200.00).setScale(2));
        assertThat(savedWaste.getDescription()).isEqualTo("High-quality recycled plastic");
        assertThat(savedWaste.getAgent().getAgentId()).isEqualTo(2L);
    }




}