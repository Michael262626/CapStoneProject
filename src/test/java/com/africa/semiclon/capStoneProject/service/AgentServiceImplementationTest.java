package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SendWasteDetailRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SendWasteDetailResponse;
import com.africa.semiclon.capStoneProject.exception.AgentExistAlreadyException;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static com.africa.semiclon.capStoneProject.data.models.Category.PLASTIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class AgentServiceImplementationTest {

    @Autowired
    private AgentService agentService;

    @Test
//    @Sql(scripts = "/db/data.sql")
    public void testThatAgentCanCreateAccount(){
        RegisterAgentRequest registerAgentRequest = new RegisterAgentRequest();
        registerAgentRequest.setEmail("praiseoyewole562@gmail.com");
        registerAgentRequest.setPassword("oluwakemisola098");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");
        Address address = new Address();
        address.setStreetName("sabo yaba");
        address.setCity("lagos state");
        address.setZipCode("12345");
//        address.setId(205L);
        address.setPostalCode("0902897");
        registerAgentRequest.setAddress(address);
        System.out.println("_________________________\n"+agentService.getAgents());
        RegisterAgentResponse response = agentService.createAccount(registerAgentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("registered successfully");
    }

    @Test
    public void testThatDuplicateAgentCannotBeRegistered_throwAgentAlreadyExistException(){
        RegisterAgentRequest registerAgentRequest = new RegisterAgentRequest();
        registerAgentRequest.setEmail("praiseoyewole563@gmail.com");
        registerAgentRequest.setPassword("oluwakemisola098");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");
        Address address = new Address();
        address.setStreetName("sabo yaba");
        address.setCity("lagos state");
        address.setZipCode("12345");
        address.setPostalCode("0902897");
        registerAgentRequest.setAddress(address);
        RegisterAgentResponse response = agentService.createAccount(registerAgentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("registered successfully");
        System.out.println("_________________________\n"+agentService.getAgents());
        assertThrows(AgentExistAlreadyException.class,()->agentService.createAccount(registerAgentRequest));

    }

    @Test
    public void testAgentCanSendUserWasteDetails(){
        SendWasteDetailRequest request = new SendWasteDetailRequest();
        request.setAgentId(100L);
        request.setWasteCategory(PLASTIC);
        request.setWasteWeigh(100.5);
        request.setUserName("real");
        request.setUserId(10L);
        SendWasteDetailResponse response = agentService.sendWasteDetails(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("User waste detail sent successfully");

    }


}
