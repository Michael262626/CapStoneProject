package com.africa.semiclon.capStoneProject.dtos.response;


import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class RegisterAgentResponse {
    private String message;
    private String email;
    private Agent agent;
    private Long agentId;
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:mm a")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;
    private String username;


    public RegisterAgentResponse() {

    }


}
