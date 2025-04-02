package com.roomx.infrastructure.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {
    private String userId;
    private String userCode;
    private String email;
    private String userType;
    private String status;
}
