package com.roomx.infrastructure.multitenancy.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceFilter {
    private String name;
    private String description;
    private String note;
    private BigDecimal unitPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
