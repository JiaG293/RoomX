package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.domain.model.entity.ServicePriceHistory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ServiceEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_service", columnNames = {"service_code"})
})
public class ServiceEntity {
    public static final String TABLE_NAME = "service";
    public static final String COLUMN_ID_NAME = "service_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_DESCRIPTION_NAME = "description";
    public static final String COLUMN_NOTE_NAME = "note";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";
    public static final String COLUMN_SERVICECODE_NAME = "service_code";
    public static final String COLUMN_STATUS_NAME = "status";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 32)
    @Column(name = COLUMN_SERVICECODE_NAME, length = 32)
    private String serviceCode;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 500)
    @Column(name = COLUMN_DESCRIPTION_NAME, length = 500)
    private String description;

    @Size(max = 500)
    @Column(name = COLUMN_NOTE_NAME, length = 500)
    private String note;

    @Column(name = COLUMN_STATUS_NAME, length = Integer.MAX_VALUE)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

    @ElementCollection
    @CollectionTable(name = "image_url", joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "entity_type")
    @Column(name = "url")
    @OrderColumn(name="image_order")
    private List<String> imageUrls = new ArrayList<>();


    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("validFrom DESC")
    private List<ServicePriceHistoryEntity> priceHistories = new ArrayList<>();

}