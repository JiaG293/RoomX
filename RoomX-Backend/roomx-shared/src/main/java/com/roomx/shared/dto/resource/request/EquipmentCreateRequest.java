package com.roomx.shared.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentCreateRequest {
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> imageUrls;
    private BigDecimal unitPrice;
}
