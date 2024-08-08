package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SendWasteDetailResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateAgentProfileResponse;
import com.africa.semiclon.capStoneProject.response.CollectWasteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentService {
    RegisterAgentResponse createAccount(RegisterAgentRequest request);

    List<Agent> getAgents();

    SendWasteDetailResponse sendWasteDetails(SendWasteDetailRequest request);

    Agent findAgentById(FindAgentRequest findAgentRequest);

    UpdateAgentProfileResponse updateProfile(UpdateAgentProfileRequest request);

    CollectWasteResponse collectWaste(CollectWasteRequest collectWasteRequest);

}
