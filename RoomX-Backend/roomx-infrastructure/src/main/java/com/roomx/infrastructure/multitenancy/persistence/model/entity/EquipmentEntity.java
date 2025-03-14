package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = EquipmentEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_equipment", columnNames = {"equipment_code"})
})
public class EquipmentEntity {
    public static final String TABLE_NAME = "equipment";
    public static final String COLUMN_ID_NAME = "equipment_id";
    public static final String COLUMN_EQUIPMENTCODE_NAME = "equipment_code";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_BRAND_NAME = "brand";
    public static final String COLUMN_DESCRIPTION_NAME = "description";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 32)
    @Column(name = COLUMN_EQUIPMENTCODE_NAME, length = 32)
    private String equipmentCode;

    @Size(max = 500)
    @NotNull
    @Column(name = COLUMN_NAME_NAME, nullable = false, length = 500)
    private String name;

    @Column(name = COLUMN_BRAND_NAME, length = Integer.MAX_VALUE)
    private String brand;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}