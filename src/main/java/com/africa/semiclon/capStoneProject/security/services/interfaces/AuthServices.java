package com.africa.semiclon.capStoneProject.security.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ApiResponse;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;


public interface AuthServices {
    ApiResponse<RegisterResponse> register(RegisterRequest request);
    void blacklist(String token);
    boolean isTokenBlacklisted(String token);
}
