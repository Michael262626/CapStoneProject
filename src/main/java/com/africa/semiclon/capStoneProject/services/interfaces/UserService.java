package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;

public interface UserService {
    
    User getById(long l);

    CreateUserResponse register(CreateUserRequest createUserRequest);

    UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest);
}
