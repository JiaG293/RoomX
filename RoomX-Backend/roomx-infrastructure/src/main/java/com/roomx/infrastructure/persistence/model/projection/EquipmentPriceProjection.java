package com.roomx.infrastructure.persistence.model.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface EquipmentPriceProjection {
    UUID getEquipmentId();
    String getName();
    String getEquipmentCode();
    String getDescription();
    String getBrand();
    String getQuantity();
    BigDecimal getPrice();
}
