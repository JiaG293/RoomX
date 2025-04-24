package com.roomx.infrastructure.persistence.model.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface ServicePriceProjection {
    UUID getServiceId();
    String getName();
    String getServiceCode();
    String getDescription();
    String getNote();
    String getQuantity();
    BigDecimal getPrice();
}
