package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.infrastructure.persistence.dto.BookingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface BookingEntityService {
    Page<Booking> filterSearchPageBooking(BookingFilter filter, Pageable pageable);
}
