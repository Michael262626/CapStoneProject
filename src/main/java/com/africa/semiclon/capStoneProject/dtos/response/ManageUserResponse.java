package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.data.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ManageUserResponse {
    private List<User> users ;
}
