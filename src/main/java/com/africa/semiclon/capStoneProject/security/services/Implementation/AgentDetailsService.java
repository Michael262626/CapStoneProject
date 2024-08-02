package com.africa.semiclon.capStoneProject.security.services.Implementation;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.AgentRepository;
import com.africa.semiclon.capStoneProject.security.model.SecuredUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
public class AgentDetailsService implements UserDetailsService {
    @Autowired
    private AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Trying to get user by username: {}", username);
        Agent agent = agentRepository.findByUsername(username.toLowerCase())
                .orElseThrow(()-> new UsernameNotFoundException("Invalid username or password"));
        log.info("Found user with username: {}", username);
        return new AgentDetails(agent);
    }
}
