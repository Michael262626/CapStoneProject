package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAgentProfileRequest {
    private Address address;
    private String email;
}
