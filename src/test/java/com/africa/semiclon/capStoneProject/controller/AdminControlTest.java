package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.PaymentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.WithdrawRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreatePlanResponse;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/db/data.sql")
@AutoConfigureMockMvc
public class AdminControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testMakePaymentToUserSuccess() throws Exception {
        PaymentRequest request = new PaymentRequest();
        CreatePlanResponse response = new CreatePlanResponse();

        Mockito.when(transactionService.makePaymentToUser(Mockito.any(PaymentRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/payments/make-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"adminId\": \"300\", \"userId\": \"10\", \"amount\": 1000}"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testMakePaymentToUserFailure() throws Exception {
        PaymentRequest request = new PaymentRequest(); // Populate as needed
        Mockito.when(transactionService.makePaymentToUser(Mockito.any(PaymentRequest.class)))
                .thenThrow(new AdminException("Admin not found"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/make-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"adminId\": \"1\", \"userId\": \"2\", \"amount\": 1000 }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Admin not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testProcessWithdrawalSuccess() throws Exception {
        // Prepare request object
        WithdrawRequest request = new WithdrawRequest(); // Populate as needed

        Mockito.doNothing().when(transactionService).processWithdrawal(Mockito.any(WithdrawRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/process-withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"10\", \"amount\": 500 }"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testProcessWithdrawalFailure() throws Exception {
        WithdrawRequest request = new WithdrawRequest(); // Populate as needed

        Mockito.doThrow(new UserNotFoundException("User not found")).when(transactionService)
                .processWithdrawal(Mockito.any(WithdrawRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/process-withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"2\", \"amount\": 500 }"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}