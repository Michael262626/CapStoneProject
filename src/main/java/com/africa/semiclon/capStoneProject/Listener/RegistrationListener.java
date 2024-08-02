package com.africa.semiclon.capStoneProject.Listener;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.event.RegistrationEvent;
import com.africa.semiclon.capStoneProject.services.interfaces.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationListener
        implements ApplicationListener<RegistrationEvent> {

    private final AgentService agentService;
    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        Agent agent = event.getAgent();
        String verificationToken = UUID.randomUUID().toString();
        agentService.saveAgentVerificationToken(agent,verificationToken);
        String url  = event.getApplicationUrl() + "/register/verifyEmail?token=" +verificationToken;
        log.info("click the link to verify to complete your registration : {}" , url);
    }
}
