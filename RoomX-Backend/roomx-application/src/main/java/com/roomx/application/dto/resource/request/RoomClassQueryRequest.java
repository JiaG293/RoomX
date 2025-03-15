package com.roomx.application.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomClassQueryRequest {
    private String id;
    private String roomClassCode;
    private BigDecimal basePrice;
    private int capacity;
    private Instant createdAt;
    private Instant updatedAt;
}
