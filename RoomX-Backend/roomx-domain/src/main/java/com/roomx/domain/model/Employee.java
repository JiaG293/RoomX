package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private UUID departmentId;
}
