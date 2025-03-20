package com.roomx.domain.model.aggrerate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionDate {
    private UUID id;
    private Instant startDate;
    private Instant endDate;
    private String description;
    private String name;
    private String exceptionDateType;



}
