package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;

public interface AdminService {
    RegisterResponse registerAdmin(RegisterRequest registerRequest);

}
