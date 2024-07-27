package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.*;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.africa.semiclon.capStoneProject.data.models.Category.PLASTIC;
import static com.africa.semiclon.capStoneProject.data.models.Category.POLYTHENEBAG;
import static org.assertj.core.api.Assertions.assertThat;


@Sql(scripts = {"/db/data.sql"})
@SpringBootTest
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private WasteRepository wasteRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        Agent agent = new Agent();
        agent.setAgentId(1L);
        agent.setUsername("Agent");
        agentRepository.save(agent);

        Agent agent1 = new Agent();
        agent1.setAgentId(2L);
        agent1.setUsername("Agent");
        agentRepository.save(agent1);

        Waste waste1 = new Waste();
        waste1.setType(PLASTIC);
        waste1.setQuantity("10kg");
        waste1.setPrice(BigDecimal.valueOf(500.00));
        waste1.setAgent(agent);
        waste1.setWasteCollectionDate(LocalDateTime.now().minusDays(1));
        wasteRepository.save(waste1);

        Waste waste2 = new Waste();
        waste2.setType(POLYTHENEBAG);
        waste2.setQuantity("5kg");
        waste2.setPrice(BigDecimal.valueOf(200.00));
        waste2.setWasteCollectionDate(LocalDateTime.now().minusDays(2));
        wasteRepository.save(waste2);
    }

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

    @Test
    public void testGenerateWasteReport(){
        GenerateWasteReportRequest request = new GenerateWasteReportRequest();
        request.setStartDate(LocalDateTime.now().minusDays(5));
        request.setEndDate(LocalDateTime.now());
        WasteReportResponse response = adminService.generateWasteReport(request);

        assertThat(response).isNotNull();
        assertThat(response.getReportItems()).isNotEmpty();
        assertThat(response.getMessage()).isEqualTo("Report generated successfully");

        WasteReport reportItem = response.getReportItems().getFirst();
        assertThat(reportItem.getCategory());
        assertThat(reportItem.getQuantity()).isEqualTo("10kg");
        assertThat(reportItem.getAssignedAgent()).isEqualTo("Agent");

    }

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


}