package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/manageUsers")
    public ResponseEntity<ManageUserResponse> manageUsers(@RequestBody ManageUsersRequest manageUsersRequest) {
        ManageUserResponse response = adminService.manageUsers(manageUsersRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllWaste")
    public ResponseEntity<ViewWasteResponse> viewAllWaste(@RequestBody ViewWasteRequest viewWasteRequest) {
        ViewWasteResponse response = adminService.viewAllWaste(viewWasteRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/assignWasteToAgent")
    public ResponseEntity<AssignWasteResponse> assignWasteToAgent(@RequestBody AssignWasteRequest assignWasteRequest) {
        AssignWasteResponse response = adminService.assignWasteToAgent(assignWasteRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/generateWasteReport")
    public ResponseEntity<WasteReportResponse> generateWasteReport(@RequestBody GenerateWasteReportRequest generateWasteReportRequest) {
        WasteReportResponse response = adminService.generateWasteReport(generateWasteReportRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/sendNotification")
    public ResponseEntity<NotificationResponse> SendNotification(@RequestBody NotificationRequest notificationRequest) {
        NotificationResponse response = adminService.sendNotificationRequest(notificationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping ("/deleteUser")
    public ResponseEntity<DeleteUserResponse> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        DeleteUserResponse response = adminService.deleteUser(deleteUserRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping ("/registerAgent")
    public ResponseEntity<RegisterAgentResponse> registerAgent(@RequestBody RegisterAgentRequest registerAgentRequest) {
        RegisterAgentResponse response = adminService.registerAgent(registerAgentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping ("/registerWasteForSale")
    public ResponseEntity<RegisterWasteResponse> registerWaste(@RequestBody RegisterWasteRequest registerWasteRequest) {
        RegisterWasteResponse response = adminService.registerWasteForSale(registerWasteRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




}
