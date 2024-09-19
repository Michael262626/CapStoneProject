package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.constants.LocalDateTimeDeserializer;
import com.africa.semiclon.capStoneProject.constants.LocalDateTimeSerializer;
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
import com.africa.semiclon.capStoneProject.services.interfaces.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.africa.semiclon.capStoneProject.constants.ApiConstants.*;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final PayStackRepository payStackRepository;

    @Value("${paystack.secret.api.key}")
    private String paystackSecretKey;

    private final ObjectMapper objectMapper;

    public PaymentServiceImpl(UserRepository userRepository, PayStackRepository payStackRepository) {
        this.userRepository = userRepository;
        this.payStackRepository = payStackRepository;

        // Configure ObjectMapper
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer()); // Make sure this class is accessible
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        this.objectMapper.registerModule(module);
    }

    @Override
    public CreatePlanResponse createPlanResponse(CreatePlanRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(PAYSTACK_INIT + "/plan");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + paystackSecretKey);

            BigDecimal roundedAmount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
            BigDecimal amountInKobo = roundedAmount.multiply(BigDecimal.valueOf(100));
            request.setAmount(amountInKobo);

            post.setEntity(new StringEntity(objectMapper.writeValueAsString(request)));

            HttpResponse response = client.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_CREATED) {
                return objectMapper.readValue(responseBody, CreatePlanResponse.class);
            } else {
                log.error("HTTP error code: {}", statusCode);
                log.error("Response body: {}", responseBody);
                throw new RuntimeException("Failed with HTTP error code: " + statusCode);
            }
        } catch (IOException e) {
            log.error("Error occurred while making API request", e);
            throw new RuntimeException("Error occurred while making API request", e);
        }
    }

    @Override
    public PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, Long id) {
        PaymentPayStack paymentPaystack = null;
        PaymentVerificationResponse paymentVerificationResponse = null;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);

            HttpResponse response = client.execute(request);
            StringBuilder result = new StringBuilder();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }

            paymentVerificationResponse = objectMapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || !paymentVerificationResponse.getStatus().equals("true")) {
                throw new RuntimeException("An error occurred during payment verification");
            } else if (paymentVerificationResponse.getData().getStatus().equals("success")) {
                User appUser = userRepository.getReferenceById(id);
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                paymentPaystack = PaymentPayStack.builder()
                        .user(appUser)
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(LocalDateTime.parse(paymentVerificationResponse.getData().getPaidAt()))
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .pricingPlanType(pricingPlanType)
                        .timeCreated(LocalDateTime.now())
                        .build();
            }
        } catch (IOException ex) {
            log.error("Error occurred during payment verification", ex);
            throw new RuntimeException("Paystack payment verification failed", ex);
        }

        if (paymentPaystack != null) {
            payStackRepository.save(paymentPaystack);
        }
        return paymentVerificationResponse;
    }

    @Override
    public InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            StringEntity postingString = new StringEntity(objectMapper.writeValueAsString(request));
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey.trim());

            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("Response Status Code: {}", statusCode);
            log.info("Response Body: {}", responseBody);

            if (statusCode == HttpStatus.SC_OK) {
                return objectMapper.readValue(responseBody, InitializePaymentResponse.class);
            } else {
                throw new RuntimeException("Paystack returned status code " + statusCode +
                        ". Response: " + responseBody);
            }
        } catch (IOException e) {
            log.error("IO Exception occurred while processing the request", e);
            throw new RuntimeException("IO Exception occurred while processing the request", e);
        }
    }
}
