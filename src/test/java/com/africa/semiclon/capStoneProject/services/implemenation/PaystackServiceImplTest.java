package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpVersion;
import org.apache.http.impl.io.EmptyInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaystackServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private HttpClient httpClient;

    @Value("${paystack.secret.key}")
    private String paystackSecretKey = "test_secret_key";

    @InjectMocks
    private PaystackServiceImpl paystackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paystackService = new PaystackServiceImpl(paystackSecretKey, userRepository, transactionRepository);
        paystackService.setHttpClient(httpClient); // Inject the mocked HttpClient
    }

    @Test
    void createPlanResponseTest() throws Exception {
        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        CreatePlanResponse.Data expectedData = CreatePlanResponse.Data.builder()
                .name("Test Plan")
                .plan_code("PLN_12345")
                .description("This is a test plan")
                .amount(BigDecimal.valueOf(5000))
                .interval("monthly")
                .reference("REF_12345")
                .gatewayResponse("Successful")
                .paidAt("2023-07-25T15:03:00Z")
                .createdAt("2023-07-25T15:00:00Z")
                .channel("card")
                .currency("NGN")
                .ipAddress("127.0.0.1")
                .build();

        CreatePlanResponse expectedResponse = CreatePlanResponse.builder()
                .status(true)
                .message("Plan created")
                .data(expectedData)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(expectedResponse);
        HttpResponse httpResponse = new BasicHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, 201, "Created")
        );
        httpResponse.setEntity(new StringEntity(jsonResponse));

        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);

        CreatePlanResponse actualResponse = paystackService.createPlanResponse(createPlanRequest);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.isStatus());
        assertEquals("Plan created", actualResponse.getMessage());
        assertNotNull(actualResponse.getData());
        assertEquals(expectedData.getName(), actualResponse.getData().getName());
        assertEquals(expectedData.getPlan_code(), actualResponse.getData().getPlan_code());
        assertEquals(expectedData.getDescription(), actualResponse.getData().getDescription());
        assertEquals(expectedData.getAmount(), actualResponse.getData().getAmount());
        assertEquals(expectedData.getInterval(), actualResponse.getData().getInterval());
        assertEquals(expectedData.getReference(), actualResponse.getData().getReference());
        assertEquals(expectedData.getGatewayResponse(), actualResponse.getData().getGatewayResponse());
        assertEquals(expectedData.getPaidAt(), actualResponse.getData().getPaidAt());
        assertEquals(expectedData.getCreatedAt(), actualResponse.getData().getCreatedAt());
        assertEquals(expectedData.getChannel(), actualResponse.getData().getChannel());
        assertEquals(expectedData.getCurrency(), actualResponse.getData().getCurrency());
        assertEquals(expectedData.getIpAddress(), actualResponse.getData().getIpAddress());
    }
}
