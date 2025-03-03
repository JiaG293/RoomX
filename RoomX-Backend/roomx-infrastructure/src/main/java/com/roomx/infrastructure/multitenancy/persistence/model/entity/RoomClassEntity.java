package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RoomClassEntity.TABLE_NAME)
public class RoomClassEntity {
    public static final String TABLE_NAME = "room_class";
    public static final String COLUMN_ID_NAME = "room_class_id";
    public static final String COLUMN_BASEPRICE_NAME = "base_price";
    public static final String COLUMN_CAPACITY_NAME = "capacity";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Column(name = COLUMN_BASEPRICE_NAME)
    private BigDecimal basePrice;

    @Column(name = COLUMN_CAPACITY_NAME)
    private Integer capacity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}