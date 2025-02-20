package com.roomx.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String employeeId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userType;
}
