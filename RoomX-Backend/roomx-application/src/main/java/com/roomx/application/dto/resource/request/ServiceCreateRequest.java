package com.roomx.application.dto.resource.request;

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
public class ServiceCreateRequest {
    private String name;
    private String description;
    private String note;
    private BigDecimal unitPrice;
}
