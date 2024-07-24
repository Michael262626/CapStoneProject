package com.africa.semiclon.capStoneProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
    private String email;
    private String password;
}
