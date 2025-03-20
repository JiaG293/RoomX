package com.roomx.application.service.booking;

import com.roomx.shared.dto.booking.request.ServiceBookingRequest;
import com.roomx.application.mapper.ServiceRequestAppMapper;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.domain.repository.BookingRequestRepository;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.domain.repository.ServiceRequestRepository;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceRequestAppService {
    private final ServiceRequestAppMapper serviceRequestAppMapper;
    private final ServiceRequestRepository serviceRequestRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public List<ServiceRequest> createAll(String bookingRequestId, List<ServiceBookingRequest> requests) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_REQUEST_NOT_FOUND, bookingRequestId));

        List<ServiceRequest> serviceRequests = requests.stream()
                .map(request -> {
                    Service serviceDomain = serviceRepository.findById(request.getServiceId())
                            .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, request.getServiceId()));

                    return ServiceRequest.builder()
                            .id(new ServiceRequestId(UUID.fromString(bookingRequestId), UUID.fromString(request.getServiceId())))
                            .bookingRequest(bookingRequest)
                            .service(serviceDomain)
                            .quantity((short) request.getQuantity())
                            .build();
                })
                .toList();

        return serviceRequestRepository.saveAll(serviceRequests);
    }
}
