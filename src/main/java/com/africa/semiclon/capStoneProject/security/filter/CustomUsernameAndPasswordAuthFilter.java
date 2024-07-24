package com.africa.semiclon.capStoneProject.security.filter;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;

@AllArgsConstructor
public class CustomUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response){
        try{
            //1. retrieve auth credentials from request bosy
            InputStream requestBodyStream = request.getInputStream();
            //2.convert json data from 1. to  java object
            LoginRequest loginRequest = mapper.readValue(requestBodyStream, LoginRequest.class);
            //3. create authentication object  dat is not yet auth.ted
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            //4. Pass the uauthenticated Authentication object to d uthenticationManager
            //4b. get back authentication result from authentication manager
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            //put the authentication result in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public CustomUsernameAndPasswordAuthFilter() {
        super();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken( generateAccessToken(authResult));
        loginResponse.setMessage("Successful Authentication");
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(true);
        response.getOutputStream().write(mapper.writeValueAsBytes(authResult));
        response.flushBuffer();
        chain.doFilter(request,response);
    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT.create()
                .withIssuer("waste_app")
                .withArrayClaim("roles",
                        getClaimsFrom(authResult.getAuthorities()))
                .withExpiresAt(Instant.now()
                        .plusSeconds(60*60))
                .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> grantedAuthorities){
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream()
                .write(mapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
    }
}
