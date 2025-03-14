package com.roomx.application.dto.resource.request;

import com.roomx.domain.model.aggrerate.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
