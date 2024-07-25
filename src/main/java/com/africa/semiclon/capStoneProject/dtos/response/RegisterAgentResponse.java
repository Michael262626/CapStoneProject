package com.africa.semiclon.capStoneProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterAgentResponse {
    private String message;
    private String email;
    private Long id;
    private String username;
}
