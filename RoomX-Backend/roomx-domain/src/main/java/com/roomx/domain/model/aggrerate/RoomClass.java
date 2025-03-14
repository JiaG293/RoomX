package com.roomx.domain.model.aggrerate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomClass {
    private UUID id;
    private String roomClassCode;
    private BigDecimal basePrice;
    private Integer capacity;

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();



}
