package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = UserConfigEntity.TABLE_NAME)
public class UserConfigEntity {
    public static final String TABLE_NAME = "user_config";
    public static final String COLUMN_ID_NAME = "user_config_id";
    public static final String COLUMN_CONFIGTYPE_NAME = "config_type";
    public static final String COLUMN_GENERATEAUTO_NAME = "generate_auto";
    public static final String COLUMN_TIMEBUFFERBOOKING_NAME = "time_buffer_booking";
    public static final String COLUMN_TIMEDURATION_NAME = "time_duration";


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 64)
    @Column(name = COLUMN_CONFIGTYPE_NAME, length = 64)
    private String configType;

    @ColumnDefault("true")
    @Column(name = COLUMN_GENERATEAUTO_NAME)
    private Boolean generateAuto;

    @Column(name = COLUMN_TIMEBUFFERBOOKING_NAME)
    private Integer timeBufferBooking;

    @Column(name = COLUMN_TIMEDURATION_NAME)
    private Integer timeDuration;

}