package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Address;
import com.africa.semiclon.capStoneProject.dtos.request.CollectWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateAgentProfileRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.exception.AgentExistAlreadyException;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateAgentProfileResponse;
import com.africa.semiclon.capStoneProject.exception.AgentNotFoundException;
import com.africa.semiclon.capStoneProject.exception.CollectWasteResponse;
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
        registerAgentRequest.setPassword("Oluwakemisola098@");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");
        System.out.println("_________________________\n"+agentService.getAgents());
        RegisterAgentResponse response = agentService.createAccount(registerAgentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("registered successfully");
    }

    @Test
    public void testThatDuplicateAgentCannotBeRegistered_throwAgentAlreadyExistException(){
        RegisterAgentRequest registerAgentRequest = new RegisterAgentRequest();
        registerAgentRequest.setEmail("praiseoyewole563@gmail.com");
        registerAgentRequest.setPassword("Oluwake@misola098");
        registerAgentRequest.setUsername("dark_royal");
        registerAgentRequest.setPhoneNumber("09028979349");
        RegisterAgentResponse response = agentService.createAccount(registerAgentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("registered successfully");
        System.out.println("_________________________\n"+agentService.getAgents());
        assertThrows(AgentExistAlreadyException.class,()->agentService.createAccount(registerAgentRequest));

    }

//    @Test
//    public void testAgentCanSendUserWasteDetails(){
//        SendWasteDetailRequest request = new SendWasteDetailRequest();
//        request.setAgentId(100L);
//        request.setWasteCategory(PLASTIC);
//        request.setWasteWeigh(100.5);
//        request.setUsername("real");
//        request.setUserId(10L);
//        SendWasteDetailResponse response = agentService.sendWasteDetails(request);
//        assertThat(response).isNotNull();
//        assertThat(response.getMessage()).contains("User waste detail sent successfully");
//
//    }

    @Test
    @Sql(scripts = "/db/data.sql")
    public void testThatAgentCanUpdateProfile(){
        UpdateAgentProfileRequest request = new UpdateAgentProfileRequest();
        Address address = new Address();
        request.setEmail("real@gmail.com");
        address.setPostalCode("12345");
        address.setZipCode("123456");
        address.setCity("Lagos");
        address.setStreetName("Abulegba");
        request.setAddress(address);

        UpdateAgentProfileResponse response = agentService.updateProfile(request);
        assertThat(response.getMessage()).contains("Profile updated successfully");
        assertThat(response).isNotNull();

    }

    @Test
    @Sql(scripts = "/db/data.sql")
    public void testThatAgentProfileCannotBeUpdatedWithUnregisteredEmail(){
        UpdateAgentProfileRequest request = new UpdateAgentProfileRequest();
        Address address = new Address();
        request.setEmail("yoo@gmail.com");
        address.setPostalCode("1234534");
        address.setZipCode("12345612");
        address.setCity("Lagos");
        address.setStreetName("Abulegba1");
        request.setAddress(address);
        assertThrows(AgentNotFoundException.class,()->agentService.updateProfile(request));
    }

    @Test
    public void testThatAgentCanCollectWasteFromUser() {
        CollectWasteRequest request = new CollectWasteRequest();
        request.setWasteCategory(PLASTIC);
        request.setUsername("user");
        request.setUserId(10L);
        request.setWasteWeigh(10.5);
        request.setAgentId(107L);
        CollectWasteResponse response = agentService.collectWaste(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Waste collected successfully");


    }

    @Test
    public void testWasteCanBeCollectedByAgent() {

        String portName = "COM1";
        CollectWasteRequest request = new CollectWasteRequest();
        request.setAgentId(107L);
        request.setUsername("user");
        request.setUserId(10L);

        agentService.initiateWasteCollection(portName);

        agentService.startWeighingProcess(portName, request);
        request.setWasteWeigh(50.0);
        CollectWasteResponse response = agentService.collectWaste(request);

        assertThat(response.getMessage()).isEqualTo("Waste collected successfully");
        assertThat(response.getWasteWeigh()).isEqualTo(50.0);
        assertThat(response.getWasteCategory()).isEqualTo(PLASTIC);
        assertThat(response.getAgentId()).isEqualTo(107L);
        assertThat(response.getUserName()).isEqualTo("user");
        assertThat(response.getUserId()).isEqualTo(10L);
    }


}
