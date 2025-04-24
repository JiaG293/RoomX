package com.roomx.infrastructure.persistence.dto;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RoomFilter extends BaseFilter {
    private String id;
    private String roomCode;
    private String status;
    private BigDecimal startPrice;
    private BigDecimal endPrice;

    private String branchId;
    private String buildingId;
    private String floorId;

    private Integer capacity;


}
