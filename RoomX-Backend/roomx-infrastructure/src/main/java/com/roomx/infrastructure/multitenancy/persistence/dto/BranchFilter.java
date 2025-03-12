package com.roomx.infrastructure.multitenancy.persistence.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchFilter {
    private String branchCode;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
}
