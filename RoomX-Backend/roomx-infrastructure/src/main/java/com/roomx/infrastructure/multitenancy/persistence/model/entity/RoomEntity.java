package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RoomEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_phong_hop_ma_phong", columnNames = {"room_code"})
})
public class RoomEntity {
    public static final String TABLE_NAME = "room";
    public static final String COLUMN_ROOMCODE_NAME = "room_code";
    public static final String COLUMN_ID_NAME = "room_id";
    public static final String COLUMN_STATUS_NAME = "status";
    public static final String COLUMN_DESCRIPTION_NAME = "description";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 32)
    @Column(name = COLUMN_ROOMCODE_NAME, length = 32)
    private String roomCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceEntity place;

    @Size(max = 32)
    @Column(name = COLUMN_STATUS_NAME, length = 32)
    private String status;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_class_id", nullable = false)
    private RoomClassEntity roomClassEntity;


}