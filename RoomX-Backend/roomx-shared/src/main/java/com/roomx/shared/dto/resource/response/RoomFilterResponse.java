package com.roomx.shared.dto.resource.response;

import com.roomx.shared.dto.resource.base.EquipmentPriceDto;
import com.roomx.shared.dto.resource.base.ServicePriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomFilterResponse {
    private String id;
    private String roomCode;
    private List<String> imageUrls;
    private String description;
    private String status;

    private String floorPlaceId;
    private String buildingPlaceId;
    private String branchPlaceId;


    private String roomClassId;
    private String roomClassCode;
    private Integer capacity;

    private List<EquipmentPriceDto> equipments;
    private List<ServicePriceDto> services;

}
