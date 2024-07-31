package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.repository.PayStackRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "/db/data.sql")
public class PaymentServiceImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImplTest.class);
    @Autowired
    private PaymentServiceImpl paymentService;


    @Test
    public void testCreatePlanResponse() {
        CreatePlanRequest createPlanRequest = new CreatePlanRequest();
        createPlanRequest.setName("Test Plan");
        createPlanRequest.setAmount(BigDecimal.valueOf(1000));

        CreatePlanResponse createPlanResponse = paymentService.createPlanResponse(createPlanRequest);

        assertNotNull(createPlanResponse);
        assertEquals("Plan created", createPlanResponse.getMessage());
    }
    @Test
    public void testInitializePaymentResponse() throws Exception {
        InitializePaymentRequest initializePaymentRequest = new InitializePaymentRequest();
        initializePaymentRequest.setAmount(BigDecimal.valueOf(1000));
        initializePaymentRequest.setEmail("test@example.com");

        InitializePaymentResponse initializePaymentResponse = paymentService.initializePaymentResponse(initializePaymentRequest);
        log.info("Initial payment response: {}", initializePaymentResponse);
        assertNotNull(initializePaymentResponse);
        assertTrue(initializePaymentResponse.getStatus());
        assertEquals("Authorization URL created", initializePaymentResponse.getMessage());
    }
}
