package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.domain.model.entity.ServiceRoomClass;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
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
    private Integer capacity;
    private String status;
    private List<RoomClassPriceHistory> roomClassPriceHistories;

    @Builder.Default
    private Set<EquipmentRoomClass> equipments = new HashSet<>();
    @Builder.Default
    private Set<ServiceRoomClass> services = new HashSet<>();

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();


}
