package com.africa.semiclon.capStoneProject.services.interfaces;


import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;

public interface UserService  {
    CreateUserResponse register(CreateUserRequest createUserRequest);
}
