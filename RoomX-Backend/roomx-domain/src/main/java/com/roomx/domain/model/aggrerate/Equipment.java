package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    private EquipmentPriceHistory price;

}
