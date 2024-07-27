package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Admin;
import com.africa.semiclon.capStoneProject.data.models.PricingPlanType;
import com.africa.semiclon.capStoneProject.data.models.Transaction;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.GetBalanceRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class TransactionServiceImpl  {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final PaystackServiceImpl paystackServiceImpl;

    public CreatePlanResponse makePaymentToUser(Long adminId, Long userId, BigDecimal amount) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        createPlanRequest.setName(user.getUsername());
        createPlanRequest.setAmount(amount);
        createPlanRequest.setEmail(user.getEmail());


        CreatePlanResponse createPlanResponse = paystackServiceImpl.createPlanResponse(createPlanRequest);

        if (createPlanResponse != null && "success".equals(createPlanResponse.getMessage())) {
            Transaction transaction = Transaction.builder()
                    .adminId(adminId)
                    .userId(userId)
                    .reference(createPlanResponse.getData().getReference())
                    .amount(amount)
                    .gatewayResponse(createPlanResponse.getData().getGatewayResponse())
                    .paidAt(createPlanResponse.getData().getPaidAt())
                    .createdAt(createPlanResponse.getData().getCreatedAt())
                    .channel(createPlanResponse.getData().getChannel())
                    .currency(createPlanResponse.getData().getCurrency())
                    .ipAddress(createPlanResponse.getData().getIpAddress())
                    .planType(PricingPlanType.PAYMENT)
                    .createdOn(new Date())
                    .build();

            transactionRepository.save(transaction);
        }

        return createPlanResponse;
    }

    public void processWithdrawal(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GetBalanceRequest getBalanceRequest = new GetBalanceRequest();
        getBalanceRequest.setUserId(userId);

        BigDecimal userBalance = getUserBalance(getBalanceRequest);
        if (userBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();

        initializePaymentRequest.setAmount(amount);
        initializePaymentRequest.setEmail(user.getEmail());

        InitializePaymentResponse initializePaymentResponse = paystackServiceImpl.initializePaymentResponse(initializePaymentRequest);

        if (initializePaymentResponse != null && "success".equals(initializePaymentResponse.getMessage())) {
            Transaction transaction = Transaction.builder()
                    .userId(userId)
                    .reference(initializePaymentResponse.getData().getReference())
                    .amount(amount)
                    .gatewayResponse(initializePaymentResponse.getData().getGatewayResponse())
                    .paidAt(initializePaymentResponse.getData().getPaidAt())
                    .createdAt(initializePaymentResponse.getData().getCreatedAt())
                    .channel(initializePaymentResponse.getData().getChannel())
                    .currency(initializePaymentResponse.getData().getCurrency())
                    .ipAddress(initializePaymentResponse.getData().getIpAddress())
                    .planType(PricingPlanType.WITHDRAWAL)
                    .createdOn(new Date())
                    .build();
            transactionRepository.save(transaction);

            deductUserBalance(userId, amount);
        }
    }

    private BigDecimal getUserBalance(GetBalanceRequest request) {
        User user = userRepository.findByUserId(request.getUserId());
        if (user != null) {
            return user.getBalance();
        }
        throw new RuntimeException("User not found");
    }

    private void deductUserBalance(Long userId, BigDecimal amount) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            BigDecimal currentBalance = user.getBalance();
            if (currentBalance.compareTo(amount) >= 0) {
                user.setBalance(currentBalance.subtract(amount));
                userRepository.save(user);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
