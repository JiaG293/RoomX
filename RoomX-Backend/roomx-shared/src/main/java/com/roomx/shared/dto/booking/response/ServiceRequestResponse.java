package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.PriceHistoryResponse;
import com.roomx.shared.dto.resource.response.ServiceResponse;
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
public class ServiceRequestResponse {
    private String id;
    private String serviceCode;
    private String name;
    private List<String> imageUrls;
    private BigDecimal unitPrice;
    private int quantity;
}
