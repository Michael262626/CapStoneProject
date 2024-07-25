package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.ManageUsersRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ManageUserResponse;

public interface AdminService {

    ManageUserResponse manageUsers(ManageUsersRequest request);

}
