package com.africa.semiclon.capStoneProject.security.filters;

import com.africa.semiclon.capStoneProject.dtos.request.LoginRequest;
import com.africa.semiclon.capStoneProject.dtos.response.BaseResponse;
import com.africa.semiclon.capStoneProject.dtos.response.LoginResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            InputStream requestBodyStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(requestBodyStream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult =
                    authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(generateAccessToken(authResult));
        loginResponse.setMessage("Successful authentication");
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setCode(OK.value());
        baseResponse.setSuccess(true);
        baseResponse.setData(loginResponse);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
        chain.doFilter(request, response);
    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT.create()
                .withIssuer("waste_app")
                .withClaim("principal", authResult.getPrincipal().toString())
                .withClaim("credentials", authResult.getCredentials().toString())
                .withArrayClaim("roles", extractAuthorities(authResult.getAuthorities()))
                .withExpiresAt(now().plus(1, HOURS))
                .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] extractAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setCode(UNAUTHORIZED.value());
        baseResponse.setSuccess(false);
        response.setStatus(UNAUTHORIZED.value());
        response.getOutputStream()
                .write(objectMapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
    }
}
