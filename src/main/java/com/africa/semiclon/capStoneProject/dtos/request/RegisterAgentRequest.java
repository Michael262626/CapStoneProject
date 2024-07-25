package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RegisterAgentRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private Address address;
    private String username;

}
