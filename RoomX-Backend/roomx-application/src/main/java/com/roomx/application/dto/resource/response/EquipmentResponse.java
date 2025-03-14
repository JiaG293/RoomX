package com.roomx.application.dto.resource.response;

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
