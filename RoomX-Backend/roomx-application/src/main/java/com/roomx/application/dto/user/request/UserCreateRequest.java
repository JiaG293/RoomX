package com.roomx.application.dto.user.request;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserCreateRequest {
    String userCode;
    String password;
    String email;
    String type;
}
