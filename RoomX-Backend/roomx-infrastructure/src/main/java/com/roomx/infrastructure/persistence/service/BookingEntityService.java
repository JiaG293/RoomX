package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.infrastructure.persistence.dto.BookingFilter;
import com.roomx.infrastructure.persistence.dto.BookingGetFilter;
import com.roomx.infrastructure.persistence.model.dto.BookingDto;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.persistence.model.projection.BookingProjection;
import com.roomx.shared.base.BookingListDto;
import com.roomx.shared.dto.booking.response.BookingMiniumResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface BookingEntityService {
    Page<Booking> filterSearchPageBooking(BookingFilter filter, Pageable pageable);

    Page<Booking> filterSearchPageBookingWithUser(BookingGetFilter filter, String userId, Pageable pageable);

    Page<BookingMiniumResponse> findBookingsByTimeRangeAndUserId(LocalDate startDate, LocalDate endDate, String userId, Pageable pageable);

    Page<BookingMiniumResponse> findBookingsByTimeRangeAndUserIdAndStatus(LocalDate startDate, LocalDate endDate, String userId, String status, Pageable pageable);

    Page<BookingMiniumResponse> findBookingsByTimeRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<BookingMiniumResponse> findBookingsByTimeRangeAndStatus(LocalDate startDate, LocalDate endDate, String status, Pageable pageable);

    Page<BookingMiniumResponse> findBookingsByTimeRangeAndStatusList(LocalDate startDate, LocalDate endDate, List<String> statusList, Pageable pageable);

    List<BookingProjection> findAllById(String bookingId);
}
