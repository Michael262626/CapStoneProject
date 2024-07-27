package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.models.WasteReport;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.exception.AgentNotFoundException;
import com.africa.semiclon.capStoneProject.exception.WasteNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final WasteRepository wasteRepository;
    private final AgentRepository agentRepository;
    private final EmailService emailService;


    public AdminServiceImpl(UserRepository userRepository, WasteRepository wasteRepository, AgentRepository agentRepository,  EmailService emailService) {
        this.userRepository = userRepository;

        this.wasteRepository = wasteRepository;
        this.agentRepository = agentRepository;
        this.emailService = emailService;

    }

    @Override
    public ManageUserResponse manageUsers(ManageUsersRequest request) {
        List<User> users = userRepository.findAll();
        ManageUserResponse response = new ManageUserResponse();
        response.setUsers(users);
        return response;
    }

    @Override
    public ViewWasteResponse viewAllWaste(ViewWasteRequest viewWasteRequest) {
        List<Waste> wastes = wasteRepository.findAll();
        ViewWasteResponse response = new ViewWasteResponse();
        response.setWastes(wastes);
        return response;

    }

    @Override
    public AssignWasteResponse assignWasteToAgent(AssignWasteRequest request) {
        Waste waste = wasteRepository.findById(request.getWasteId())
                .orElseThrow(() -> new WasteNotFoundException("Waste not found"));
        Agent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new AgentNotFoundException("Agent not found"));
        waste.setAgent(agent);
        wasteRepository.save(waste);

        AssignWasteResponse response = new AssignWasteResponse();

        response.setMessage("Successfully assigned");
        response.setWasteId(waste.getWasteId());
        response.setAgentId(agent.getAgentId());
        return response;

    }

    @Override
    public WasteReportResponse generateWasteReport(GenerateWasteReportRequest request) {
        List<Waste> wastes = wasteRepository.findAllByWasteCollectionDateBetween(request.getStartDate(), request.getEndDate());

        List<WasteReport> reportItems = wastes.stream().map(waste -> {
            WasteReport report = new WasteReport();
            report.setWasteId(waste.getWasteId());
            report.setCategory(waste.getType());
            report.setQuantity(waste.getQuantity());
            report.setPrice(waste.getPrice());
            report.setAssignedAgent(waste.getAgent() != null ? waste.getAgent().getUsername() : "Unassigned");
            report.setCollectionDate(waste.getWasteCollectionDate());
            return report;
        }).collect(Collectors.toList());

        WasteReportResponse response = new WasteReportResponse();
        response.setReportItems(reportItems);
        response.setMessage("Report generated successfully");
        return response;
    }

    @Override
    public NotificationResponse sendNotificationRequest(NotificationRequest notificationRequest) {
        emailService.sendEmail(notificationRequest.getRecipientEmail(), notificationRequest.getTitle(), notificationRequest.getContent());

        NotificationResponse response = new NotificationResponse();
        response.setId(1L);
        response.setMessage("Notification sent successfully");
        return response;

    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteRequest) {
        DeleteUserResponse response = new DeleteUserResponse();

        if (userRepository.existsById(deleteRequest.getUserId())) {
            userRepository.deleteById(deleteRequest.getUserId());
            response.setMessage("User deleted successfully");
        } else {
            response.setMessage("User not found");
        }

        return response;
    }

}

