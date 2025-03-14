package com.roomx.application.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.roomx.domain.model.enums.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceSelectBoxRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String branchId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String building;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String floor;
    private String placeType = PlaceType.ROOM.toString();
}
