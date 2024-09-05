package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.data.models.User;


import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(long l);

    CreateUserResponse register(CreateUserRequest createUserRequest);

    UpdateUserResponse updateProfile(UpdateUserRequest updateUserRequest);

    SellWasteResponse sellWaste(SellWasteRequest sellWasteRequest);
    WeightCollectedResponse getTotalWeightCollectedByUser(User user);
    SearchResponse getUserIdByUsername(String username);

}
