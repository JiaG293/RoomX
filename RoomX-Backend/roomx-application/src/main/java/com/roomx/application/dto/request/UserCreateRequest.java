package com.roomx.application.dto.request;

import lombok.*;

@Builder
@Value
public class UserCreateRequest {
    String employeeId;
    String password;
    String email;
    String type;
}
