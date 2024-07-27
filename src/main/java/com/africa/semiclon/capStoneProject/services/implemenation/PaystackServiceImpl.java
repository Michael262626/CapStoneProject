package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.repository.TransactionRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.PaystackService;
import com.google.gson.Gson;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Setter
@Service
public class PaystackServiceImpl implements PaystackService {

    private static final Logger logger = LoggerFactory.getLogger(PaystackServiceImpl.class);

    private static final String PAYSTACK_INIT = "https://api.paystack.co/plan";
    private static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    private static final int STATUS_CODE_CREATED = 201;
    private static final int STATUS_CODE_OK = 200;

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final String paystackSecretKey;
    @Setter
    private HttpClient httpClient;

    public PaystackServiceImpl(@Value("${paystack.secret.key}") String paystackSecretKey, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.paystackSecretKey = paystackSecretKey;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.httpClient = HttpClientBuilder.create().build(); // Default HttpClient
    }

    @Override
    public CreatePlanResponse createPlanResponse(CreatePlanRequest createPlanRequest) {
        return executePaystackRequest(createPlanRequest, PAYSTACK_INIT, STATUS_CODE_CREATED, CreatePlanResponse.class);
    }

    @Override
    public InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest initializePaymentRequest) {
        return executePaystackRequest(initializePaymentRequest, PAYSTACK_INITIALIZE_PAY, STATUS_CODE_OK, InitializePaymentResponse.class);
    }

    private <T> T executePaystackRequest(Object request, String url, int expectedStatusCode, Class<T> responseType) {
        T responseObj = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(request));
            HttpPost post = new HttpPost(url);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == expectedStatusCode) {
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                }
            } else {
                throw new Exception("Paystack is unable to process the request at the moment " +
                        "or something is wrong with the request.");
            }

            responseObj = gson.fromJson(result.toString(), responseType);
        } catch (Throwable ex) {
            logger.error("An error occurred while processing the request: ", ex);
        }

        return responseObj;
    }
}
