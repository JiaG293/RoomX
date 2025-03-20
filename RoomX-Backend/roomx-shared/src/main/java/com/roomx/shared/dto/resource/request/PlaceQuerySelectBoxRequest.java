package com.roomx.shared.dto.resource.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceQuerySelectBoxRequest {
    private String floor = null;
    private String building = null;
}
