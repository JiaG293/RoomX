package com.roomx.infrastructure.multitenancy.persistence.dto;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.model.aggrerate.RoomClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomFilter {
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


}
