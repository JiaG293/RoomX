package com.roomx.shared.dto.user.request;

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
    private String userCode = null;
    private String email = null;
    private String userType = null;
    private boolean status = true;
}

