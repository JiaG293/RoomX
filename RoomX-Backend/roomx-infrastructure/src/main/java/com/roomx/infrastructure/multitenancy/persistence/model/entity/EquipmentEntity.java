package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = EquipmentEntity.TABLE_NAME)
public class EquipmentEntity {
    public static final String TABLE_NAME = "equipment";
    public static final String COLUMN_ID_NAME = "equipment_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_BRAND_NAME = "brand";
    public static final String COLUMN_DESCRIPTION_NAME = "description";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

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

}