package com.roomx.shared.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentCreateRequest {
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private BigDecimal unitPrice;
}
