package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.enums.ApprovalStatusType;
import com.roomx.domain.model.vo.ServiceRequestId;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
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
    @Builder.Default
    private String approvalStatus = ApprovalStatusType.PENDING.toString();
    private User requester;
    private Short priority;

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    @Builder.Default
    private List<EquipmentRequest> equipmentRequests = new ArrayList<>();


    //Behavior
    public void addServiceRequest(Service service, Short quantity, BigDecimal unitPrice){
        ServiceRequestId serviceRequestId = new ServiceRequestId(this.id, service.getId());
        ServiceRequest serviceRequest = new ServiceRequest(serviceRequestId, this, service, unitPrice, quantity);
        this.serviceRequests.add(serviceRequest);
    }
    /*//Domain Event example
    public void duyetDonYeuCau(String nguoiDuyet) {
        this.trangThaiDuyet = "APPROVED";
        DomainEventPublisher.instance().publish(new DonYeuCauDuocDuyet(this.id, nguoiDuyet));
    }*/

    /*public static class DonYeuCauDuocDuyet {
        private final UUID donYeuCauId;
        private final String nguoiDuyet;

        public DonYeuCauDuocDuyet(UUID donYeuCauId, String nguoiDuyet) {
            this.donYeuCauId = donYeuCauId;
            this.nguoiDuyet = nguoiDuyet;
        }

        public UUID getDonYeuCauId() {
            return donYeuCauId;
        }

        public String getNguoiDuyet() {
            return nguoiDuyet;
        }
    }*/
}
