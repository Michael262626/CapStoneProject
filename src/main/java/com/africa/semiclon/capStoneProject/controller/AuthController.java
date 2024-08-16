package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.RegisterRequest;
import com.africa.semiclon.capStoneProject.exception.UserAlreadyExistsException;
import com.africa.semiclon.capStoneProject.security.services.interfaces.AuthServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.africa.semiclon.capStoneProject.security.utils.SecurityUtils.JWT_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthServices authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try{
            return ResponseEntity.status(CREATED).body(authService.register(request));
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(JWT_PREFIX)) {
            String token = authHeader.replace(JWT_PREFIX, "").strip();
            authService.blacklist(token);
            SecurityContextHolder.clearContext();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
