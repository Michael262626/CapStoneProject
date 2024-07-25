package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Admin;
import com.africa.semiclon.capStoneProject.data.repository.AdminRepository;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Override
    public RegisterResponse registerAdmin(RegisterRequest registerRequest) {
        Admin check = adminRepository.findByUsername(registerRequest.getUsername());
        if (check != null) {
            throw new AdminException("Username already exists");
        }
        Admin adminToBeRegistered= Admin.builder()
                        .username(registerRequest.getUsername())
                                .adminEmail(registerRequest.getEmail())
                                        .adminPassword(registerRequest.getPassword())
                                                .build();
        adminRepository.save(adminToBeRegistered);
        return modelMapper.map(adminToBeRegistered, RegisterResponse.class);
    }
}
