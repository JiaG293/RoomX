package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.entity.ServiceRoomClass;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class RoomClass {
    private UUID id;
    private String roomClassCode;
    private BigDecimal basePrice;
    private Integer capacity;
    @Builder.Default
    private Set<EquipmentRoomClass> equipments = new HashSet<>();;
    @Builder.Default
    private Set<ServiceRoomClass> services = new HashSet<>();;

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPriceEquipments = equipments.stream().map(equipmentRoomClass -> equipmentRoomClass.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPriceServices = services.stream().map(serviceRoomClass -> serviceRoomClass.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return basePrice.add(totalPriceEquipments).add(totalPriceServices).add(basePrice);
    }


}
