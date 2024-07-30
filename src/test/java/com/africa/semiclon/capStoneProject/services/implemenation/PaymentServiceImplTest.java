package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.PaymentPayStack;
import com.africa.semiclon.capStoneProject.data.models.PricingPlanType;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.PayStackRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.PaymentVerificationResponse;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static com.africa.semiclon.capStoneProject.constants.ApiConstants.PAYSTACK_INIT;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PayStackRepository payStackRepository;
    @Value("${paystack.secret.api.key}")
    private String paystackSecretKey;


    @Test
    public void testCreatePlanResponse() throws Exception {
        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        createPlanRequest.setName("Test Plan");
        createPlanRequest.setAmount(BigDecimal.valueOf(1000.00));


        CreatePlanResponse mockResponse = new CreatePlanResponse();
        mockResponse.setStatus(true);
        mockResponse.setMessage("success");
        mockResponse.setData(new CreatePlanResponse.Data(
                "Test Plan",
               BigDecimal.valueOf(100.00),
                "monthly",
                "integration_id",
                "plan_code",
                "false",
                "true",
                "USD",
                1L,
               Date.from(Instant.parse("2024-07-04T00:00:00Z")),
                Date.from(Instant.parse("2024-07-04T00:00:00Z"))
        ));

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        HttpPost post = new HttpPost(PAYSTACK_INIT);
        post.setEntity(new StringEntity(new Gson().toJson(createPlanRequest)));
        post.addHeader("Content-type", "application/json");
        post.addHeader("Authorization", "Bearer " +paystackSecretKey);

        HttpResponse response = clientBuilder.build().execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        CreatePlanResponse createPlanResponse = new Gson().fromJson(result.toString(), CreatePlanResponse.class);

        assertNotNull(createPlanResponse);
//        assertTrue(createPlanResponse.getStatus());
        assertEquals("success", createPlanResponse.getMessage());
    }
                                                    
    @Test
    public void testPaymentVerificationResponse() throws Exception {
        String reference = "ref123";
        String plan = "basic";
        Long userId = 1L;

        PaymentVerificationResponse.Data data = getData(reference);

        User mockUser = new User();
        mockUser.setUserId(userId);

       userRepository.save(mockUser);

        PaymentPayStack paymentPayStack = getPaymentPayStack(mockUser, data);

       payStackRepository.save(paymentPayStack);

        PaymentVerificationResponse response = paymentService.paymentVerificationResponse(reference, plan, userId);

        assertNotNull(response);
        assertEquals("true", response.getStatus());
    }

    private static PaymentPayStack getPaymentPayStack(User mockUser, PaymentVerificationResponse.Data data) {
        PaymentPayStack paymentPayStack = new PaymentPayStack();
        paymentPayStack.setUser(mockUser);
        paymentPayStack.setReference(data.getReference());
        paymentPayStack.setAmount(data.getAmount());
        paymentPayStack.setGatewayResponse(data.getGatewayResponse());
        paymentPayStack.setPaidAt(String.valueOf(new Date()));
        paymentPayStack.setCreatedAt(String.valueOf(data.getCreatedAt()));
        paymentPayStack.setChannel(data.getChannel());
        paymentPayStack.setCurrency(data.getCurrency());
        paymentPayStack.setIpAddress(data.getIpAddress());
        paymentPayStack.setPricingPlanType(PricingPlanType.PAYMENT);
        paymentPayStack.setTimeCreated(LocalDateTime.now());
        return paymentPayStack;
    }

    private static PaymentVerificationResponse.Data getData(String reference) {
        PaymentVerificationResponse mockResponse = new PaymentVerificationResponse();
        mockResponse.setStatus("true");
        PaymentVerificationResponse.Data data = new PaymentVerificationResponse.Data();
        data.setStatus("success");
        data.setReference(reference);
        data.setAmount(BigDecimal.valueOf(1000.00));
        data.setGatewayResponse("Gateway Response");
        data.setPaidAt("2024-01-01T00:00:00Z");
        data.setCreatedAt(LocalDateTime.parse("2024-01-01T00:00:00Z"));
        data.setChannel("channel");
        data.setCurrency("USD");
        data.setIpAddress("192.168.1.1");
        mockResponse.setData(data);
        return data;
    }

    @Test
    public void testInitializePaymentResponse() throws Exception {
        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(BigDecimal.valueOf(1000.00));
        initializePaymentRequest.setEmail("test@example.com");

        InitializePaymentResponse mockResponse = new InitializePaymentResponse();
        mockResponse.setStatus(true);
        mockResponse.setMessage("success");
        mockResponse.setData(new InitializePaymentResponse.Data(
                "authorization_url",
                "access_code",
                "reference"
        ));

        // Mocking HTTP call directly
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
        post.setEntity(new StringEntity(new Gson().toJson(initializePaymentRequest)));
        post.addHeader("Content-type", "application/json");
        post.addHeader("Authorization", "Bearer "+paystackSecretKey); // Replace with actual key

        HttpResponse response = clientBuilder.build().execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        InitializePaymentResponse initializePaymentResponse = new Gson().fromJson(result.toString(), InitializePaymentResponse.class);

        assertNotNull(initializePaymentResponse);
//        assertTrue(initializePaymentResponse.getStatus());
        assertEquals("success", initializePaymentResponse.getMessage());
    }
}
