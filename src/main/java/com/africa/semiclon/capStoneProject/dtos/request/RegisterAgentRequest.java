package com.africa.semiclon.capStoneProject.dtos.request;


import com.africa.semiclon.capStoneProject.data.models.Address;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RegisterAgentRequest {
    @Id
    private Long id;
    private String email;
    private String phoneNumber;
    private String password;
    private String username;



}
