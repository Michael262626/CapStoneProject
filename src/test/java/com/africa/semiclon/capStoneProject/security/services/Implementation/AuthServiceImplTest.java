package com.africa.semiclon.capStoneProject.security.services.Implementation;

import static com.africa.semiclon.capStoneProject.utils.TestUtils.buildRegisterRequest;

import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.dtos.response.RegisterResponse;
import com.africa.semiclon.capStoneProject.exception.UsernameExistsException;
import com.africa.semiclon.capStoneProject.security.services.interfaces.AuthServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class AuthServiceTest {

    @Autowired
    private AuthServices authService;

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