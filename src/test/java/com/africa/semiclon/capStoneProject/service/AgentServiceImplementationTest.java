package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AgentServiceImplementationTest {

    @Autowired
    private AgentService agentService;

    @Test
    public void testThatAgentCanCreateAccount(){
        RegisterAgentRequest registerAgentRequest = new RegisterAgentRequest();
        registerAgentRequest.setEmail("praiseoyewole560@gmail.com");
        registerAgentRequest.setPassword("oluwakemisola098");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");

        RegisterAgentResponse response = agentService.createAccount(registerAgentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("registered successfully");
    }


}
