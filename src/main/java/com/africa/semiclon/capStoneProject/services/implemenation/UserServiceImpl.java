package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).
                orElseThrow(()-> new UserNotFoundException(
                        String.format("user with id %d not found", id)));

    }
    }

