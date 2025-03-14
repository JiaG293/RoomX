package com.roomx.application.dto.resource.request;

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
public class EquipmentQueryRequest {
    private String equipmentCode = null;
    private String name = null;
    private String brand = null;
    private String description = null;
    private BigDecimal unitPrice = null;
    private boolean typeCompare = false;
    private Instant createdAt = null;
    private Instant updatedAt = null;
    private boolean compareType = false;
}
