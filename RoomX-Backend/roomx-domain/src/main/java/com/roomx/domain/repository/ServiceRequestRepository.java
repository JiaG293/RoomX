package com.roomx.domain.repository;

import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.vo.ServiceRequestId;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository {
    Optional<ServiceRequest> findById(ServiceRequestId serviceRequestId);
    Optional<ServiceRequest> findByServiceId(String serviceId);
    Optional<ServiceRequest> findByBookingRequestId(String bookingRequestId);
    ServiceRequest save(ServiceRequest serviceRequest);
    List<ServiceRequest> saveAll(List<ServiceRequest> listServiceRequset);
}
