package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.dtos.request.CollectWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateAgentProfileRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateAgentProfileResponse;
import com.africa.semiclon.capStoneProject.exception.AgentExistAlreadyException;
import com.africa.semiclon.capStoneProject.exception.AgentNotFoundException;
import com.africa.semiclon.capStoneProject.exception.CollectWasteResponse;
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
@RequestMapping("/api/v1/agent")
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
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AgentExistAlreadyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateAgentProfileRequest updateAgentProfileRequest){
        try{
            UpdateAgentProfileResponse response = agentService.updateProfile(updateAgentProfileRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (AgentNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/collect")
    public ResponseEntity<?> collectWasteFromUser(@RequestBody CollectWasteRequest collectWasteRequest){
        try{
            CollectWasteResponse response = agentService.collectWaste(collectWasteRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (AgentNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
