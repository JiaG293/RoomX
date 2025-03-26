package com.roomx.shared.dto.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRoomClassDetailResponse {
    private String id;
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private List<String> imageUrls;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
