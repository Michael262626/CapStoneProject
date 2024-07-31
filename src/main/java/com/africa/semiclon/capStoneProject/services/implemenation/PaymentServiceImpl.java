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
import com.africa.semiclon.capStoneProject.services.interfaces.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static com.africa.semiclon.capStoneProject.constants.ApiConstants.*;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final PayStackRepository payStackRepository;


    @Value("${paystack.secret.api.key}")
    private String paystackSecretKey;
    private final Gson gson = new Gson();

    public PaymentServiceImpl(UserRepository userRepository, PayStackRepository payStackRepository) {
        this.userRepository = userRepository;
        this.payStackRepository = payStackRepository;
    }

    @Override
    public CreatePlanResponse createPlanResponse(CreatePlanRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(PAYSTACK_INIT + "/plan");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + paystackSecretKey);

            BigDecimal roundedAmount = request.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal amountInKobo = roundedAmount.multiply(BigDecimal.valueOf(100));
            request.setAmount(amountInKobo);


            post.setEntity(new StringEntity(gson.toJson(request)));

            HttpResponse response = client.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                return gson.fromJson(responseBody, CreatePlanResponse.class);
            } else {
                log.error("HTTP error code: {}", statusCode);
                log.error("Response body: {}", responseBody);
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
        } catch (Exception e) {
            log.error("Error occurred while making API request", e);
            throw new RuntimeException("Error occurred while making API request", e);
        }
    }

    @Override
    public PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, Long id) {
        PaymentPayStack paymentPaystack = null;
        PaymentVerificationResponse paymentVerificationResponse = null;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse. getData().getStatus().equals("success")) {

                User appUser = userRepository.getReferenceById(id);
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                paymentPaystack = PaymentPayStack.builder()
                        .user(appUser)
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(String.valueOf(paymentVerificationResponse.getData().getCreatedAt()))
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .pricingPlanType(pricingPlanType)
                        .timeCreated(LocalDateTime.now())
                        .build();
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Paystack");
        }
        assert paymentPaystack != null;
        payStackRepository.save(paymentPaystack);
        return paymentVerificationResponse;
    }

    @Override
    public InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest request) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + paystackSecretKey);
            post.setEntity(new StringEntity(gson.toJson(request)));

            HttpResponse response = client.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == 200) {
                return gson.fromJson(responseBody, InitializePaymentResponse.class);
            } else {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while making API request", e);
        }
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentRequest request) {
        return null;
    }
}
