package com.roomx.infrastructure.persistence.model.entity;

import com.roomx.infrastructure.persistence.model.ids.EquipmentRoomClassEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = EquipmentRoomClassEntity.TABLE_NAME)
public class EquipmentRoomClassEntity {
    public static final String TABLE_NAME = "equipment_room_class";
    public static final String COLUMN_QUANTITY_NAME = "quantity";

    @EmbeddedId
    private EquipmentRoomClassEntityId id;

    @MapsId("roomClassId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_class_id", nullable = false)
    private RoomClassEntity roomClass;

    @MapsId("equipmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @Builder.Default
    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity = 1;

}