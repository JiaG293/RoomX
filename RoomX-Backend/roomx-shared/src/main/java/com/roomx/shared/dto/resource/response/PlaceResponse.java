package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceResponse {
    private String id;
    private BranchResponse branch;
    private String parentId;
    private String code;
    private String name;
    private String layout;
    private String placeType;
}
