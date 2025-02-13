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
public class Partner {
    private UUID partnerId;
    private String name;
    private String contactEmail;
    private String description;
}
