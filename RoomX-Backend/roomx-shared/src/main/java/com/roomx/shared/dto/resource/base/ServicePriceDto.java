package com.roomx.shared.dto.resource.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicePriceDto {
    private UUID id;
    private String name;
    private String serviceCode;
    private String description;
    private String note;
    private String quantity;
    private BigDecimal price;
}
