package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.infrastructure.persistence.dto.BookingFilter;
import com.roomx.infrastructure.persistence.dto.BookingGetFilter;
import com.roomx.infrastructure.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.infrastructure.persistence.repository.specification.BookingRequestSpecification;
import com.roomx.infrastructure.persistence.repository.specification.BookingSpecification;
import com.roomx.infrastructure.persistence.service.BookingEntityService;
import com.roomx.infrastructure.persistence.service.PageableQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEntityServiceImpl implements BookingEntityService {
    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final BookingEntityMapper bookingEntityMapper;

    @Override
    public Page<Booking> filterSearchPageBooking(BookingFilter filter, Pageable pageable) {
        var spec = BookingSpecification.searchFilterBooking(filter);

        var bookingEntityPage = jpaBookingEntityRepository.findAll(spec, pageable);

        return bookingEntityPage.map(bookingEntityMapper::toDomain);
    }

    @Override
    public Page<Booking> filterSearchPageBookingWithUser(BookingGetFilter filter, String userId, Pageable pageable) {
        var spec = BookingSpecification.searchFilterBookingWithUser(filter, userId);

        var bookingEntityPage = jpaBookingEntityRepository.findAll(spec, pageable);

        return bookingEntityPage.map(bookingEntityMapper::toDomain);
    }
}
