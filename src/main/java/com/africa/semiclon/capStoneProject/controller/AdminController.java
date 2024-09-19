package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import com.africa.semiclon.capStoneProject.services.interfaces.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final TransactionService transactionService;

    @PostMapping("/makePayment")
    public ResponseEntity<?> makePaymentToUser(@RequestBody PaymentRequest request) {
        try {
            InitializePaymentResponse response = transactionService.makePaymentToUser(request);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/processWithdrawal")
    public ResponseEntity<?> processWithdrawal(@RequestBody WithdrawRequest request) {
        try {
            transactionService.processWithdrawal(request);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/manageUsers")
    public ResponseEntity<?> manageUsers(ManageUsersRequest manageUsersRequest) {
        try {
            ManageUserResponse response = adminService.manageUsers(manageUsersRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/viewAllWaste")
    public ResponseEntity<?> viewAllWaste( ViewWasteRequest viewWasteRequest) {
        try {
            ViewWasteResponse response = adminService.viewAllWaste(viewWasteRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/assignWasteToAgent")
    public ResponseEntity<?> assignWasteToAgent(@RequestBody AssignWasteRequest assignWasteRequest) {
        try {
            AssignWasteResponse response = adminService.assignWasteToAgent(assignWasteRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/generateWasteReport")
    public ResponseEntity<?> generateWasteReport(@RequestBody GenerateWasteReportRequest generateWasteReportRequest) {
        try {
            WasteReportResponse response = adminService.generateWasteReport(generateWasteReportRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            NotificationResponse response = adminService.sendNotificationRequest(notificationRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        try {
            DeleteUserResponse response = adminService.deleteUser(deleteUserRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/registerAgent")
    public ResponseEntity<?> registerAgent(@RequestBody RegisterAgentRequest registerAgentRequest) {
        try {
            RegisterAgentResponse response = adminService.registerAgent(registerAgentRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/registerWasteForSale")
    public ResponseEntity<?> registerWaste(@RequestBody RegisterWasteRequest registerWasteRequest) {
        try {
            RegisterWasteResponse response = adminService.registerWasteForSale(registerWasteRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
