package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private Authority role;
}
