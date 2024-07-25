package com.africa.semiclon.capStoneProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUserRequest {
    private String phoneNumber;
    private String password;
}
