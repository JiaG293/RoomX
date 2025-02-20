package com.roomx.domain.employee.model;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    private String number;
    private String type;
    private int priority;
}
