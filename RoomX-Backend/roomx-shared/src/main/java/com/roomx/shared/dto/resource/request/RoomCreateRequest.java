package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateRequest {
    private String roomCode;
    private String placeId;
    private String roomClassId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String status;
}
