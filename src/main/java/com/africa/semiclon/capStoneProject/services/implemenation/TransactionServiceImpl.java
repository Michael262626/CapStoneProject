package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.PricingPlanType;
import com.africa.semiclon.capStoneProject.data.models.Transaction;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.WithdrawRequest;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.PaymentService;
import com.africa.semiclon.capStoneProject.services.interfaces.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentService paystackService;

    @Transactional
    public InitializePaymentResponse makePaymentToUser(PaymentRequest request) {
        // Find the user by ID
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Prepare payment initialization request
        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(BigDecimal.valueOf(request.getAmount()));
        initializePaymentRequest.setEmail(user.getEmail());

        // Call Paystack service to initialize payment and get the response
        InitializePaymentResponse initializePaymentResponse = paystackService.initializePaymentResponse(initializePaymentRequest);

        // Validate payment response and generate the URL
        if (initializePaymentResponse != null && Boolean.TRUE.equals(initializePaymentResponse.getStatus())) {
            // Save the transaction details
            transactionRepository.save(Transaction.builder()
                    .userId(request.getUserId())
                    .reference(initializePaymentResponse.getData().getReference())
                    .amount(BigDecimal.valueOf(request.getAmount()))
                    .gatewayResponse("Payment initialized")
                    .paidAt(String.valueOf(new Date()))
                    .createdAt(String.valueOf(new Date()))
                    .channel("Online")
                    .currency("NGN")
                    .planType(PricingPlanType.PAYMENT)
                    .build());

            // Return the payment response which contains the URL
            return initializePaymentResponse;
        } else {
            log.error("Failed to initialize payment: {}", initializePaymentResponse);
            throw new IllegalStateException("Payment initialization failed");
        }
    }

    @Transactional
    public void processWithdrawal(WithdrawRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Handle null balance, assuming null balance is considered as zero
        BigDecimal userBalance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;

        // Check if user has enough balance
        if (userBalance.compareTo(BigDecimal.valueOf(request.getAmount())) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Prepare payment initialization request
        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(BigDecimal.valueOf(request.getAmount()));
        initializePaymentRequest.setEmail(user.getEmail());

        // Call Paystack service to initialize payment
        InitializePaymentResponse initializePaymentResponse = paystackService.initializePaymentResponse(initializePaymentRequest);

        // Validate payment response
        if (initializePaymentResponse != null && Boolean.TRUE.equals(initializePaymentResponse.getStatus())) {
            // Save the transaction details
            transactionRepository.save(Transaction.builder()
                    .userId(request.getUserId())
                    .reference(initializePaymentResponse.getData().getReference())
                    .amount(BigDecimal.valueOf(request.getAmount()))
                    .gatewayResponse("Withdrawal initialized")
                    .paidAt(String.valueOf(new Date())) // Ideally use proper date handling
                    .createdAt(String.valueOf(new Date()))
                    .channel("Online")
                    .currency("NGN")
                    .planType(PricingPlanType.WITHDRAWAL)
                    .build());

            // Update user balance
            user.setBalance(userBalance.subtract(BigDecimal.valueOf(request.getAmount())));
            userRepository.save(user);
        } else {
            log.error("Failed to initialize payment: {}", initializePaymentResponse);
            throw new IllegalStateException("Payment initialization failed");
        }
    }
}
