package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Service;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ServicePriceHistory {
    private UUID id;
    private Service service;
    private BigDecimal unitPrice;
    private Instant validStart;
    private Instant validEnd;
}
