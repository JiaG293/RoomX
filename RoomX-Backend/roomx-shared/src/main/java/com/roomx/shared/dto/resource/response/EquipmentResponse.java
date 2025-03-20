package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentResponse {
    private String id;
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private BigDecimal unitPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
