package com.africa.semiclon.capStoneProject.security.services.Implementation;

import static com.africa.semiclon.capStoneProject.utils.TestUtils.buildRegisterRequest;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.dtos.request.LoginRequest;
import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ApiResponse;
import com.africa.semiclon.capStoneProject.dtos.response.LoginResponse;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.security.services.interfaces.AuthServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class AuthServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthServices authService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void registerUserTest() {
        RegisterRequest request = buildRegisterRequest();
        var response = authService.register(request);
        assertThat(response).isNotNull();
        RegisterResponse responseData = response.getData();
        assertThat(responseData.getId()).isNotNull();
        assertThat(responseData.getMessage()).contains("success");
    }

    @Test
    @DisplayName("test that registration fails for non unique username")
    public void registerUserWithExistingUsername_throwsExceptionTest() {
        RegisterRequest request = buildRegisterRequest();
        var response = authService.register(request);
        assertThat(response).isNotNull();
        assertThrows(UsernameExistsException.class, ()-> authService.register(request));
    }
}