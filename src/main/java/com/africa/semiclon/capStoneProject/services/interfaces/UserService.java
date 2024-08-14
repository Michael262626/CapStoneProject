package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.data.models.User;


import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.request.NotificationRequest;
import com.africa.semiclon.capStoneProject.dtos.request.SellWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.request.UpdateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.SellWasteResponse;
import com.africa.semiclon.capStoneProject.dtos.response.UpdateUserResponse;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(long l);

    CreateUserResponse register(CreateUserRequest createUserRequest);

    UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest);

    SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest);

}
