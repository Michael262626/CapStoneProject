package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.PricingPlanType;
import com.africa.semiclon.capStoneProject.data.models.Transaction;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class TransactionServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentService paystackService;

    @Transactional
    public CreatePlanResponse makePaymentToUser(PaymentRequest request) {
        checkAdmin(request);
        User user = checkUser(request);

        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        createPlanRequest.setName(user.getUsername());
        createPlanRequest.setAmount(request.getAmount());

        CreatePlanResponse createPlanResponse = paystackService.createPlanResponse(createPlanRequest);

        if (createPlanResponse != null && Boolean.TRUE.equals(createPlanResponse.getStatus())) {
            Transaction transaction = Transaction.builder()
                    .adminId(request.getAdminId())
                    .userId(request.getUserId())
                    .amount((createPlanResponse.getData().getAmount()))
                    .gatewayResponse("Payment created")
                    .paidAt(String.valueOf(new Date()))
                    .createdAt(String.valueOf(new Date()))
                    .channel("Online")
                    .currency(createPlanResponse.getData().getCurrency())
                    .ipAddress("Unknown")
                    .planType(PricingPlanType.PAYMENT)
                    .createdOn(new Date())
                    .build();

            transactionRepository.save(transaction);
        } else {
            log.error("Failed to create plan response: {}", createPlanResponse);
        }

        return createPlanResponse;
    }

    private User checkUser(PaymentRequest request) {
        return userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void checkAdmin(PaymentRequest request) {
        adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new AdminException("Admin not found"));
    }

    @Transactional
    public void processWithdrawal(WithdrawRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        GetBalanceRequest getBalanceRequest = new GetBalanceRequest();
        getBalanceRequest.setUserId(request.getUserId());

        BigDecimal userBalance = getUserBalance(getBalanceRequest);
        if (userBalance.compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(request.getAmount());
        initializePaymentRequest.setEmail(user.getEmail());

        InitializePaymentResponse initializePaymentResponse = paystackService.initializePaymentResponse(initializePaymentRequest);

        if (initializePaymentResponse != null && Boolean.TRUE.equals(initializePaymentResponse.getStatus())) {
            Transaction transaction = Transaction.builder()
                    .userId(request.getUserId())
                    .reference(initializePaymentResponse.getData().getReference())
                    .amount(request.getAmount())
                    .gatewayResponse("Withdrawal initialized") // Adjust if needed
                    .paidAt(String.valueOf(new Date())) // Assuming payment is considered paid immediately
                    .createdAt(String.valueOf(new Date())) // Assuming current time for creation
                    .channel("Online") // Example, adjust based on your requirements
                    .currency("NGN") // Assuming NGN, adjust based on your requirements
                    .ipAddress("Unknown") // Set this accordingly
                    .planType(PricingPlanType.WITHDRAWAL)
                    .createdOn(new Date())
                    .build();
            transactionRepository.save(transaction);

            deductUserBalance(request.getUserId(), request.getAmount());
        } else {
            log.error("Failed to initialize payment response: {}", initializePaymentResponse);
        }
    }

    private BigDecimal getUserBalance(GetBalanceRequest request) {
        return userRepository.findById(request.getUserId())
                .map(User::getBalance)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void deductUserBalance(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        BigDecimal currentBalance = user.getBalance();
        if (currentBalance.compareTo(amount) >= 0) {
            user.setBalance(currentBalance.subtract(amount));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }
}
