package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.data.models.Authority;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminResponse {
    private Long id;
    private String username;
    private String email;
    private Authority authority;
}
