package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.dtos.request.AdminRequest;
import com.africa.semiclon.capStoneProject.exception.AdminException;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;

import static com.africa.semiclon.capStoneProject.data.models.Authority.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    void registerAdmin() {
        AdminRequest adminRequest = AdminRequest.builder()
                .username("username")
                .email("user@gmail.com")
                .password("password")
                .authority(Collections.singleton(ADMIN))
                .build();
        var adminResponse = adminService.registerAdmin(adminRequest);
        assertThat(adminResponse).isNotNull();
        assertThat(adminResponse.getUsername()).isEqualTo("username");
    }

    @Test
    void registerAdminThatAlreadyExistsThrowsException() {
        //registerAdmin();
        AdminRequest adminRequest =  AdminRequest.builder()
                .username("username1")
                .email("user@gmail.com")
                .password("password")
                .authority(Collections.singleton(ADMIN))
                .build();
        adminService.registerAdmin(adminRequest);
        assertThatThrownBy(() -> adminService.registerAdmin(adminRequest))
                .isInstanceOf(AdminException.class)
                .hasMessageContaining("Username already exists");
    }
}