package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceResponse {
    private String id;
    private String serviceCode;
    private String name;
    private String description;
    private String note;
    private List<String> imageUrls;
    private PriceHistoryResponse price;
    private String status;
}
