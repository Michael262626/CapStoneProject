package com.africa.semiclon.capStoneProject.security.providers;

import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {




    private UserDetailsService userDetailsService;
    private final UserRepository userRepository;



    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isMatches = passwordEncoder.matches(password, userDetails.getPassword());
        if (isMatches) return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), "[PROTECTED]", userDetails.getAuthorities());
        throw new BadCredentialsException("Invalid username or password");
    }


    @Override
    public boolean supports(Class<?> authType) {
        return authType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
