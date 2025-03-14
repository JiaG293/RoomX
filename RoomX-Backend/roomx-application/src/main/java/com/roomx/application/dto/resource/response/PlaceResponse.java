package com.roomx.application.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceResponse {
    private String id;
    private BranchResponse branch;
    private String slug;
    private String floor;
    private String building;
    private String name;
    private String layout;
    private String placeType;
}
