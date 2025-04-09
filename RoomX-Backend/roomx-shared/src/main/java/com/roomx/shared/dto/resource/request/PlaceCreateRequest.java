package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceCreateRequest {

    private String parentId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String code;
    private String layout;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String name;

    @Pattern(regexp = "^(BRANCH|BUILDING|FLOOR)$", message = "valid.place.place_type")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String placeType;
}
