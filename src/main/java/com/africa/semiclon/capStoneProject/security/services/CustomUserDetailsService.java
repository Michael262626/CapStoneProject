//package com.africa.semiclon.capStoneProject.security.services;
//
//import com.maverickstube.maverickshub.data.models.User;
//import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
//import com.maverickstube.maverickshub.security.models.SecureUser;
//import com.maverickstube.maverickshub.services.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            User user = userService.getByUsername(username);
//            return new SecureUser(user);
//        } catch (UserNotFoundException e) {
//            throw new UsernameNotFoundException(e.getMessage());
//        }
//    }
//}
