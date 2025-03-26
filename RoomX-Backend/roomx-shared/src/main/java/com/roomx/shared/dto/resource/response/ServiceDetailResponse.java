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
public class ServiceDetailResponse {
    private String id;
    private String serviceCode;
    private String name;
    private String description;
    private String note;
    private List<String> imageUrls;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
