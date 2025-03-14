package com.roomx.application.dto.resource.request;

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

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String branchId;
    private String slug;
    private String floor;
    private String building;
    private String name;
    private String layout;

    @Pattern(regexp = "^(ROOM|DEPARTMENT|BRANCH)$", message = "valid.user.create.type")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String placeType;
}
