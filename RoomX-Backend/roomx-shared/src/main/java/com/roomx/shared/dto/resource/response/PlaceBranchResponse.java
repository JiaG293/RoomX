package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceBranchResponse {
    private String id;
    private String branchId;
    private String code;
    private String name;
    private String layout;
    private String placeType;
}
