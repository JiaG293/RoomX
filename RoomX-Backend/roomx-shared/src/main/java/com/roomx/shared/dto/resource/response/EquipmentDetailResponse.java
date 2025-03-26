package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDetailResponse {
    private String id;
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private List<String> imageUrls;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
