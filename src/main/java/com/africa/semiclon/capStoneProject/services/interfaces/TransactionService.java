package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.WithdrawRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;

public interface TransactionService {
    CreatePlanResponse makePaymentToUser(PaymentRequest request);
    void processWithdrawal(WithdrawRequest request);
}
