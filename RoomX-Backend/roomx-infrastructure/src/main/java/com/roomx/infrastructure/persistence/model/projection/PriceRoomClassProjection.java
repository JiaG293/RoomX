package com.roomx.infrastructure.persistence.model.projection;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface PriceRoomClassProjection {
    BigDecimal getTotalPrice();
    BigDecimal getBasePrice();
    Instant getValidFrom();
    Instant getValidEnd();


}
