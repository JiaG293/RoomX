package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = ServiceEntity.TABLE_NAME)
public class ServiceEntity {
    public static final String TABLE_NAME = "service";
    public static final String COLUMN_ID_NAME = "service_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_DESCRIPTION_NAME = "description";
    public static final String COLUMN_NOTE_NAME = "note";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 500)
    @Column(name = COLUMN_DESCRIPTION_NAME, length = 500)
    private String description;

    @Size(max = 500)
    @Column(name = COLUMN_NOTE_NAME, length = 500)
    private String note;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}