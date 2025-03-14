package com.roomx.application.dto.resource.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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
