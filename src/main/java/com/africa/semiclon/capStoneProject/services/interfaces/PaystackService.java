package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.CreatePlanRequest;
import com.africa.semiclon.capStoneProject.dtos.request.InitializePaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.dtos.response.InitializePaymentResponse;

public interface PaystackService {
    CreatePlanResponse createPlanResponse(CreatePlanRequest createPlanRequest);
    InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest initializePaymentRequest);
}
