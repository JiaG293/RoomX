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
public class ServiceResponse {
    private String id;
    private String serviceCode;
    private String name;
    private String description;
    private String note;
    private BigDecimal unitPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
