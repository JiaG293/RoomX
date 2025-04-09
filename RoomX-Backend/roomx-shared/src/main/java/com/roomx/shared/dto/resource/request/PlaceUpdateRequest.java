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
public class PlaceUpdateRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String code;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String layout;
}
