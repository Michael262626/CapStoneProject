package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.ManageUsersRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ManageUserResponse;
import com.africa.semiclon.capStoneProject.exception.UserAlreadyExistsException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
//    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
//        this.modelMapper = modelMapper;
    }

    @Override
    public ManageUserResponse manageUsers(ManageUsersRequest request) {

        List<User> users = userRepository.findAll();
        ManageUserResponse response = new ManageUserResponse();
        response.setUsers(users);
        return response;
    }
}
