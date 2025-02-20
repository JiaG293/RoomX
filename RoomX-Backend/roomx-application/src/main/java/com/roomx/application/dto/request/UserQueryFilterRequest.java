package com.roomx.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryFilterRequest {
    private String userId = null;
    private String employeeId = null;
    private String email = null;
    private String userType = null;
    private boolean status = true;
}

