package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
@Table(name = ServicePriceHistoryEntity.TABLE_NAME)
public class ServicePriceHistoryEntity {
    public static final String TABLE_NAME = "service_price_history";
    public static final String COLUMN_ID_NAME = "service_price_history_id";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";
    public static final String COLUMN_VALIDFROM_NAME = "valid_from";
    public static final String COLUMN_VALIDEND_NAME = "valid_end";
    public static final String COLUMN_ACTIVE_NAME = "active";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

    @Column(name = COLUMN_VALIDFROM_NAME)
    private Instant validFrom;

    @Column(name = COLUMN_VALIDEND_NAME)
    private Instant validEnd;

    @Column(name = COLUMN_ACTIVE_NAME)
    private boolean active;


}