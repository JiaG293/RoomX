package com.roomx.shared.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceUpdateRequest {
    private String branchId;
    private String slug;
    private String floor;
    private String building;
    private String name;
    private String layout;
}
