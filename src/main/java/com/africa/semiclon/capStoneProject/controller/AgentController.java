package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.event.RegistrationEvent;
import com.africa.semiclon.capStoneProject.exception.AgentExistAlreadyException;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agents")
public class AgentController {

    private final AgentService agentService;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public List<Agent> getAgents() {
        return agentService.getAgents();
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterAgentRequest registerUserRequest, final HttpServletRequest request1) {
        try {
            RegisterAgentResponse response = agentService.createAccount(registerUserRequest);
            Agent agent = response.getAgent();
            publisher.publishEvent(new RegistrationEvent(agent, applicationUrl(request1)));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AgentExistAlreadyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    private String applicationUrl(HttpServletRequest request1) {
        return "https://" + request1.getServerName() + ":" + request1.getServerPort() + request1.getContextPath();
    }
}
