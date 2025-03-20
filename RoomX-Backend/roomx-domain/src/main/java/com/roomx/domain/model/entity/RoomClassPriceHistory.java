package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.RoomClass;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class RoomClassPriceHistory {
    private UUID id;
    private RoomClass roomClass;
    private BigDecimal basePrice;
    private BigDecimal totalPrice;
    private Instant validEnd;
    private Instant validFrom;
}
