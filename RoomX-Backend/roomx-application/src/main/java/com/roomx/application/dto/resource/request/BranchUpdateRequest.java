package com.roomx.application.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchUpdateRequest {

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String phoneNumber;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String email;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String address;
}
