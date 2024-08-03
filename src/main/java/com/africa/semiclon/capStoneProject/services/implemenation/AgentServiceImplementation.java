package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.controller.VerificationToken;
import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.Authority;
import com.africa.semiclon.capStoneProject.data.models.WasteCollection;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.data.repository.VerificationRepository;
import com.africa.semiclon.capStoneProject.dtos.request.FindAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterAgentRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SendWasteDetailRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterAgentResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SendWasteDetailResponse;
import com.africa.semiclon.capStoneProject.exception.AgentExistAlreadyException;
import com.africa.semiclon.capStoneProject.exception.AgentNotFoundException;
import com.africa.semiclon.capStoneProject.security.services.interfaces.AuthServices;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class AgentServiceImplementation implements AgentService {


    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AgentRepository agentRepository;



    public AgentServiceImplementation(ModelMapper modelMapper, PasswordEncoder passwordEncoder, VerificationRepository tokenRepository, AgentRepository agentRepository, AuthServices authServices) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.agentRepository = agentRepository;
    }

    @Override
    public RegisterAgentResponse createAccount(RegisterAgentRequest request) {
        verifyAgentExistence(request.getEmail());
        Agent agent = modelMapper.map(request,Agent.class);
        agent.setAuthorities(new HashSet<>());
        var authorities = agent.getAuthorities();
        authorities.add(Authority.AGENT);
        agent.setPassword(passwordEncoder.encode(request.getPassword()));
        agent = agentRepository.save(agent);
        RegisterAgentResponse response = new RegisterAgentResponse();
        response.setEmail(request.getEmail());
        response.setMessage("registered successfully");
        response.setUsername(request.getUsername());
        response.setId(agent.getId());
        return response;

    }

    @Override
    public List<Agent> getAgents() {
        return agentRepository.findAll();
    }



    @Override
    public SendWasteDetailResponse sendWasteDetails(SendWasteDetailRequest request) {
        Agent agent = modelMapper.map(request, WasteCollection.class).getAgentId();
        agentRepository.save(agent);


        return null;
    }

    @Override
    public Agent findAgentById(FindAgentRequest findAgentRequest) {
        return agentRepository.findById(findAgentRequest.getId()).orElseThrow(()->new AgentNotFoundException("Agent not found"));

    }


    private void  verifyAgentExistence(String email){
        Agent agent = agentRepository.findByEmail(email);
        if(agent != null){
            throw new AgentExistAlreadyException(String.format("%s already exist",email));
        }
    }

    private void verifyEmail(){

    }

    private void verifyPassword(){

    }

    }


