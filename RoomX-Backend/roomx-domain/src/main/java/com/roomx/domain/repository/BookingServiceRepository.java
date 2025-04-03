package com.roomx.domain.repository;

import com.roomx.domain.model.entity.BookingService;
import com.roomx.domain.model.vo.BookingServiceId;

import java.util.List;
import java.util.Optional;

public interface BookingServiceRepository {
    Optional<BookingService> findById(BookingServiceId bookingServiceId);
    Optional<BookingService> findByBookingId(String bookingId);
    Optional<BookingService> findByServiceId(String serviceId);
    List<BookingService> findAllByBookingId(String bookingId);
    BookingService save(BookingService bookingService);
    List<BookingService> saveAll(List<BookingService> listBookingService );

}
