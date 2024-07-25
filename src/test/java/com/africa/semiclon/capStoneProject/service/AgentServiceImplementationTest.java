package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AgentServiceImplementationTest {
    private Address address;

    @Test
    public void testThatAgentCanCreateAccount(){
        RegisterAgentRequest registerAgentRequest = new RegisterAgentRequest();
        registerAgentRequest.setEmail("praiseoyewole560@gmail.com");
        registerAgentRequest.setPassword("oluwakemisola098");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");
        registerAgentRequest.setAddress();
    }
}
