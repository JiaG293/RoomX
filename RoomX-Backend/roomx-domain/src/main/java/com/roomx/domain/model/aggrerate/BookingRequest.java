package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.shared.enums.ApprovalStatusType;

import lombok.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookingRequest {
    private UUID id;
    private Room room;
    private String bookingRequestCode;
    @Builder.Default
    private String approvalStatus = ApprovalStatusType.PENDING.toString();
    private User requester;
    private Short priority;

    private String status;
    @Builder.Default
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    @Builder.Default
    private List<EquipmentRequest> equipmentRequests = new ArrayList<>();


}
