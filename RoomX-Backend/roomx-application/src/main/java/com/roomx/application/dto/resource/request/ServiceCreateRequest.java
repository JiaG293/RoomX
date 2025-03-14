package com.roomx.application.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceCreateRequest {
    private String serviceCode;
    private String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String note;
    private BigDecimal unitPrice;
}
