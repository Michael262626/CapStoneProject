package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.AdminRequest;
import com.africa.semiclon.capStoneProject.dtos.response.AdminResponse;

public interface AdminService {
    AdminResponse registerAdmin(AdminRequest adminRequest);
}
