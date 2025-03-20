package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceCreateBuildingWithFloorRequest {
    private String code;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String layout;


    private int numberFloor;
    private List<Integer> exceptions;
    private List<String> layouts;
}
