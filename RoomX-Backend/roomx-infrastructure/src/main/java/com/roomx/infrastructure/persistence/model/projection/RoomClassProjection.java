package com.roomx.infrastructure.persistence.model.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.shared.dto.resource.base.EquipmentPriceDto;
import com.roomx.shared.dto.resource.base.ServicePriceDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface RoomClassProjection {
    UUID getId();
    String getRoomClassCode();
    Integer getCapacity();
    String getStatus();

    BigDecimal getTotalPrice();
    BigDecimal getBasePrice();
    Instant getValidFrom();
    Instant getValidEnd();


    @JsonIgnore
    String getJsonServices();
    @JsonIgnore
    String getJsonEquipments();

    default List<ServicePriceDto> getServices() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getJsonServices(), new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    default List<EquipmentPriceDto> getEquipments() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getJsonEquipments(), new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}

