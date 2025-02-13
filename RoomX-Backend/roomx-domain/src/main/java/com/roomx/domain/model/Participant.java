package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    private String name;
    private String email;
    private String participantType; // E.g., EMPLOYEE, CUSTOMER, GUEST
}
