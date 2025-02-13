package com.roomx.application.dto.request;

import lombok.*;

@Builder
@Value
public class UserCreationRequest {
    String username;
    String password;
    String email;
}
