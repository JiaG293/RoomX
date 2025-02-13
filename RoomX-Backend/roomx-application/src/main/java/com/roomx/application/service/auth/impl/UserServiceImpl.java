package com.roomx.application.service.auth.impl;

import com.roomx.application.model.UserModel;
import com.roomx.application.service.auth.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaim("preferred_username");
            List<String> roles = jwt.getClaim("roles");

            return UserModel.builder().username(username).password("2").build();
        }
        else return UserModel.builder().username("1").password("2").build();
    }
}
