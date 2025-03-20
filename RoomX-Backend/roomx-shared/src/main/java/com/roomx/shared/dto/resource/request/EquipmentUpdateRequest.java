package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentUpdateRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String equipmentCode;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String brand;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private BigDecimal unitPrice;
}
