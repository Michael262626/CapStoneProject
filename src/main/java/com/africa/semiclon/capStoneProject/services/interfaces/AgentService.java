package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.dtos.request.FindAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SendWasteDetailRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SendWasteDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentService {
    RegisterAgentResponse createAccount(RegisterAgentRequest request);

    List<Agent> getAgents();

    void saveAgentVerificationToken(Agent agent, String verificationToken);

    SendWasteDetailResponse sendWasteDetails(SendWasteDetailRequest request);

    Agent findAgentById(FindAgentRequest findAgentRequest);
}
