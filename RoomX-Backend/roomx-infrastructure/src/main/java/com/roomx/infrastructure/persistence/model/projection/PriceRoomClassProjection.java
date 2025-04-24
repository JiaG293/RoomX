package com.roomx.infrastructure.persistence.model.projection;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface PriceRoomClassProjection {
    UUID getId();
    BigDecimal getPrice();
    Instant getStartDate();
    Instant getEndDate();
}
