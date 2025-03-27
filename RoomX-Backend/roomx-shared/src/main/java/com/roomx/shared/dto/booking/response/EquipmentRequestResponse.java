package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.PriceHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRequestResponse {
    private String id;
    private String equipmentCode;
    private String name;
    private List<String> imageUrls;
    private BigDecimal unitPrice;
    private int quantity;
}
