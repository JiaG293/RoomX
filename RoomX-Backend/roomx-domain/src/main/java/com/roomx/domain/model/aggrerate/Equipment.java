package com.roomx.domain.model.aggrerate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Equipment {
    private UUID id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal unitPrice;


}
