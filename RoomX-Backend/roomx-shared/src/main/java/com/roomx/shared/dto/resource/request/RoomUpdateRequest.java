package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> imageUrls;
}
