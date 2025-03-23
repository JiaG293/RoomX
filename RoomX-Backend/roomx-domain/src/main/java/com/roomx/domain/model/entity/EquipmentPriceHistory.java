package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Equipment;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class EquipmentPriceHistory {
    private UUID id;
    private Equipment equipment;
    private BigDecimal unitPrice;
    private Instant validFrom;
    private Instant validEnd;
    private boolean isActive;

    public boolean evaluateActive(){
        Instant now = Instant.now();
        return isActive && validFrom.isBefore(now) && validEnd.isAfter(now);
    }

    public boolean checkTimeValid(){
        Instant now = Instant.now();
        return validFrom.isBefore(validEnd) && !validFrom.isBefore(now);
    }
}
