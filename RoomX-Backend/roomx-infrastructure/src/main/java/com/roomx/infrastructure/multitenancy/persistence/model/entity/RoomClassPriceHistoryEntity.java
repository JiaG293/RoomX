package com.roomx.infrastructure.multitenancy.persistence.model.entity;

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
@Table(name = RoomClassPriceHistoryEntity.TABLE_NAME)
public class RoomClassPriceHistoryEntity {
    public static final String TABLE_NAME = "room_class_price_history";
    public static final String COLUMN_ID_NAME = "room_class_price_history";
    public static final String COLUMN_VALIDFROM_NAME = "valid_from";
    public static final String COLUMN_BASEPRICE_NAME = "base_price";
    public static final String COLUMN_TOTALPRICE_NAME = "total_price";
    public static final String COLUMN_VALIDEND_NAME = "valid_end";
    public static final String COLUMN_ISACTIVE_NAME = "is_active";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_class_id", nullable = false)
    private RoomClassEntity roomClass;

    @Column(name = COLUMN_VALIDFROM_NAME)
    private Instant validFrom;

    @Column(name = COLUMN_BASEPRICE_NAME)
    private BigDecimal basePrice;

    @Column(name = COLUMN_TOTALPRICE_NAME)
    private BigDecimal totalPrice;

    @Column(name = COLUMN_VALIDEND_NAME)
    private Instant validEnd;

    @Column(name = COLUMN_ISACTIVE_NAME)
    private boolean isActive;

}