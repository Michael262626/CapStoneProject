package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.UUID;

public class AgentServiceImplementation implements AgentService {


    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AgentRepository agentRepository;


    public AgentServiceImplementation(ModelMapper modelMapper, PasswordEncoder passwordEncoder, MailService mailService, AgentRepository agentRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.agentRepository = agentRepository;
    }

    @Override
    public RegisterAgentResponse createAccount(RegisterAgentRequest request) {
        Agent agent = modelMapper.map(request,Agent.class);
        agent.setAuthorities(new HashSet<>());
        var authorities = agent.getAuthorities();
        authorities.add(Authority.USER);
        agent.setPassword(passwordEncoder.encode(request.getPassword()));
        agent.setVerified(false);
        agent = agentRepository.save(agent);

        RegisterAgentResponse response = new RegisterAgentResponse();
        response.setEmail(request.getEmail());
        response.setMessage("registered successfully");
        response.setUsername(request.getUsername());
        response.setId(agent.getAgentId());
        return response;

    }


}

