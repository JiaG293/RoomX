package com.roomx.application.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomQueryRequest {
    private String id;
    private String roomCode;
    private String status;
    private String description;
//    private BigDecimal startPrice;
//    private BigDecimal endPrice;
    // place
    private String building;
    private String floor;
    private String placeName;

    // place.branch
    private String slug;
    private String branchCode;
    private String branchName;

    // room class
    private String roomClassCode;
    private int capacity;
    private boolean compareType;
}
