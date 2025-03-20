package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Equipment {
    private UUID id;
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private String status;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();


}
