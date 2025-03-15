package com.roomx.application.dto.resource.request;

import com.roomx.domain.model.aggrerate.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceQueryRequest {
    private String id = null;
    private String branchCode = null;
    private String slug = null;
    private String floor = null;
    private String building = null;
    private String name = null;
    private boolean compareType = false;
}
