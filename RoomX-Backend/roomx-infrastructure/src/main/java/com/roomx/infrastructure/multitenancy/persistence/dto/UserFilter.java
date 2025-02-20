package com.roomx.infrastructure.multitenancy.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {
    String userId;
    String employeeId;
    String email;
    String userType;
    String status;
}
