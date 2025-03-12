package com.roomx.application.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchCreateRequest {

    private String name;
    private String branchCode;
    private String phoneNumber;
    private String email;
    private String address;
}
