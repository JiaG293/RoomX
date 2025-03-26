package com.roomx.shared.dto.resource.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomClassPriceCalculateDto {
    private UUID roomClassId;
    private String roomClassCode;
    private BigDecimal basePrice;
    private BigDecimal totalServicePrice;
    private BigDecimal totalEquipmentPrice;
    private BigDecimal totalPrice;

    public RoomClassPriceCalculateDto mapToDto(Object[] result) {
        return new RoomClassPriceCalculateDto(
                (UUID) result[0],
                (String) result[1],
                (BigDecimal) result[2],
                (BigDecimal) result[3],
                (BigDecimal) result[4],
                (BigDecimal) result[5]
        );
    }
}
