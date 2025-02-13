package com.roomx.application.service.auth;

import com.roomx.application.model.UserModel;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

public interface UserService {
    UserModel getUser();
}
