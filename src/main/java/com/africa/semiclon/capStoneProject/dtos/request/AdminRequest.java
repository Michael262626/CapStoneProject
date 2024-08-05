package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AdminRequest {
    private String username;
    private String password;
    private String email;
    private Set<Authority> authority;
}
