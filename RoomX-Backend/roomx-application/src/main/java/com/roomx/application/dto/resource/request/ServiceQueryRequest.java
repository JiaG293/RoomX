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
public class ServiceQueryRequest {
    private String name = null;
    private String description = null;
    private String note = null;
    private BigDecimal unitPrice = null;
    private Instant createdAt = null;
    private Instant updatedAt = null;
    private boolean compareType = false;
}
