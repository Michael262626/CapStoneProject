package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.PaymentVerificationResponse;

public interface PaymentService {
    CreatePlanResponse createPlanResponse(CreatePlanRequest createPlanRequest) throws RuntimeException;
    PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, Long id) throws RuntimeException;

    InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest initializePaymentRequest);

}
