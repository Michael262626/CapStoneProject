package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Admin;
import com.africa.semiclon.capStoneProject.data.models.Transaction;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaystackServiceImpl paystackServiceImpl;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makePaymentToUserTest() {
        Long adminId = 1L;
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(1000);
        Admin admin = new Admin();
        User user = new User();
        user.setUsername("test_user");
        user.setEmail("test@example.com");

        CreatePlanResponse createPlanResponse = getCreatePlanResponse();

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(paystackServiceImpl.createPlanResponse(any(CreatePlanRequest.class))).thenReturn(createPlanResponse);

        CreatePlanResponse response = transactionService.makePaymentToUser(adminId, userId, amount);

        assertNotNull(response);
        assertEquals("success", response.getMessage());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    private static CreatePlanResponse getCreatePlanResponse() {
        CreatePlanResponse.Data data = new CreatePlanResponse.Data();
        data.setReference("REF_12345");
        data.setGatewayResponse("Successful");
        data.setPaidAt("2023-07-25T15:03:00Z");
        data.setCreatedAt("2023-07-25T15:00:00Z");
        data.setChannel("card");
        data.setCurrency("NGN");
        data.setIpAddress("127.0.0.1");

        CreatePlanResponse createPlanResponse = new CreatePlanResponse();
        createPlanResponse.setStatus(true);
        createPlanResponse.setMessage("success");
        createPlanResponse.setData(data);
        return createPlanResponse;
    }

    @Test
    void processWithdrawalTest() {
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(500);
        User user = new User();
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(1000));

        InitializePaymentResponse initializePaymentResponse = getInitializePaymentResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(paystackServiceImpl.initializePaymentResponse(any(InitializePaymentRequest.class))).thenReturn(initializePaymentResponse);

        transactionService.processWithdrawal(userId, amount);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(userRepository, times(1)).save(user);
        assertEquals(BigDecimal.valueOf(500), user.getBalance());
    }

    private static InitializePaymentResponse getInitializePaymentResponse() {
        InitializePaymentResponse.Data data = new InitializePaymentResponse.Data();
        data.setReference("REF_67890");
        data.setGatewayResponse("Successful");
        data.setPaidAt("2023-07-25T16:03:00Z");
        data.setCreatedAt("2023-07-25T16:00:00Z");
        data.setChannel("card");
        data.setCurrency("NGN");
        data.setIpAddress("127.0.0.1");

        InitializePaymentResponse initializePaymentResponse = new InitializePaymentResponse();
        initializePaymentResponse.setStatus(true);
        initializePaymentResponse.setMessage("success");
        initializePaymentResponse.setData(data);
        return initializePaymentResponse;
    }

    @Test
    void processWithdrawalInsufficientBalanceTest() {
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(1500);
        User user = new User();
        user.setEmail("test@example.com");
        user.setBalance(BigDecimal.valueOf(1000));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> transactionService.processWithdrawal(userId, amount));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
