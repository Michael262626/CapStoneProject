package com.africa.semiclon.capStoneProject.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAgentRequest {
    private String firstName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;


}
