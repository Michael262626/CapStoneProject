package com.africa.semiclon.capStoneProject.controller;


import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.WithdrawRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import com.africa.semiclon.capStoneProject.services.interfaces.PaymentService;
import com.africa.semiclon.capStoneProject.services.interfaces.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class AdminControl {

    private final TransactionService transactionService;
    @PostMapping("/make-payment")
    public ResponseEntity<?> makePaymentToUser(@RequestBody PaymentRequest request) {
        try {
            InitializePaymentResponse response = transactionService.makePaymentToUser(request);
            return ResponseEntity.ok(response);
        } catch (AdminException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/process-withdrawal")
    public ResponseEntity<?> processWithdrawal(@RequestBody WithdrawRequest request) {
        try {
            transactionService.processWithdrawal(request);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
