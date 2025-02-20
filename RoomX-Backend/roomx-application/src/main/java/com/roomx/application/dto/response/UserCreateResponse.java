package com.roomx.application.dto.response;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateResponse {
    private String userId;
    private String employeeId;
}
