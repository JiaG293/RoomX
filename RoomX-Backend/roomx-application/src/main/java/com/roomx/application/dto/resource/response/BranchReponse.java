package com.roomx.application.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchReponse {
    private String branchId;
    private String name;
    private String email;
    private String address;

}
