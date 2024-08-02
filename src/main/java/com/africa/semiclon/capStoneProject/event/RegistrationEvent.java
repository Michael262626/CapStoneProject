package com.africa.semiclon.capStoneProject.event;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private Agent agent;
    private String applicationUrl;
    public RegistrationEvent(Agent agent,String applicationUrl) {
        super(agent);
        this.agent = agent;
        this.applicationUrl = applicationUrl;

    }
}
