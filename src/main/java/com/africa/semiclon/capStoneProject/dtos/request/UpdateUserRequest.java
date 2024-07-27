package com.africa.semiclon.capStoneProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    private Long userId;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;


}
