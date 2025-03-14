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
public class PlaceQuerySelectBoxRequest {
    private Branch branch = null;
    private String floor = null;
    private String building = null;
}
