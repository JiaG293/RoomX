package com.roomx.application.dto.user.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateRequest {
    private String userCode;
    private String password;
    private String email;
    private String type;
}
