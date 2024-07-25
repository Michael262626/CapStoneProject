package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;

public interface AgentService {
    RegisterAgentResponse createAccount(RegisterAgentRequest request);
}
