package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.ManageUsersRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ManageUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


@SpringBootTest
public class AdminTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testAdminCanManageMultipleUsers() {
        User user1 = new User();
        user1.setUsername("User1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("User2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("password2");
        userRepository.save(user2);

        ManageUsersRequest request = new ManageUsersRequest();
        request.setAdminId(1L);

        ManageUserResponse response = adminService.manageUsers(request);
        assertThat(response).isNotNull();
        assertThat(response.getUsers()).isNotEmpty();
        assertThat(response.getUsers().size()).isGreaterThanOrEqualTo(2);
    }
}