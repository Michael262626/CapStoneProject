package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.PricingPlanType;
import com.africa.semiclon.capStoneProject.data.models.Transaction;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.WithdrawRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.exception.AdminException;
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
    public CreatePlanResponse makePaymentToUser(PaymentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        createPlanRequest.setName(user.getUsername());
        createPlanRequest.setAmount(request.getAmount());

        CreatePlanResponse createPlanResponse = paystackService.createPlanResponse(createPlanRequest);

        if (createPlanResponse != null && Boolean.TRUE.equals(createPlanResponse.getStatus())) {
            transactionRepository.save(Transaction.builder()
                    .adminId(request.getAdminId())
                    .userId(request.getUserId())
                    .amount(createPlanResponse.getData().getAmount())
                    .gatewayResponse("Payment created")
                    .paidAt(String.valueOf(new Date()))
                    .createdAt(String.valueOf(new Date()))
                    .channel("Online")
                    .currency(createPlanResponse.getData().getCurrency())
                    .planType(PricingPlanType.PAYMENT)
                    .build());
        } else {
            log.error("Failed to create plan response: {}", createPlanResponse);
        }

        return createPlanResponse;
    }

    @Transactional
    public void processWithdrawal(WithdrawRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        BigDecimal userBalance = user.getBalance();
        if (userBalance.compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(request.getAmount());
        initializePaymentRequest.setEmail(user.getEmail());

        InitializePaymentResponse initializePaymentResponse = paystackService.initializePaymentResponse(initializePaymentRequest);

        if (initializePaymentResponse != null && Boolean.TRUE.equals(initializePaymentResponse.getStatus())) {
            transactionRepository.save(Transaction.builder()
                    .userId(request.getUserId())
                    .reference(initializePaymentResponse.getData().getReference())
                    .amount(request.getAmount())
                    .gatewayResponse("Withdrawal initialized")
                    .paidAt(String.valueOf(new Date()))
                    .createdAt(String.valueOf(new Date()))
                    .channel("Online")
                    .currency("NGN")
                    .planType(PricingPlanType.WITHDRAWAL)
                    .build());

            user.setBalance(userBalance.subtract(request.getAmount()));
            userRepository.save(user);
        } else {
            log.error("Failed to initialize payment response: {}", initializePaymentResponse);
        }
    }
}
