package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ExceptionDateEntity.TABLE_NAME)
public class ExceptionDateEntity {
    public static final String TABLE_NAME = "exception_date";
    public static final String COLUMN_ID_NAME = "exception_date_id";
    public static final String COLUMN_STARTDATE_NAME = "start_date";
    public static final String COLUMN_ENDDATE_NAME = "end_date";
    public static final String COLUMN_DESCRIPTION_NAME = "description";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_EXCEPTIONDATETYPE_NAME = "exception_date_type";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Column(name = COLUMN_STARTDATE_NAME)
    private Instant startDate;

    @Column(name = COLUMN_ENDDATE_NAME)
    private Instant endDate;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_EXCEPTIONDATETYPE_NAME, nullable = false, length = 32)
    private String exceptionDateType;

}