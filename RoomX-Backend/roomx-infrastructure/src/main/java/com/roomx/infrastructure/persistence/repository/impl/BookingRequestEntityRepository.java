package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.repository.BookingRequestRepository;
import com.roomx.infrastructure.persistence.mapper.BookingRequestEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingRequestEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookingRequestEntityRepository implements BookingRequestRepository {
    private final JpaBookingRequestEntityRepository jpaBookingRequestEntityRepository;
    private final BookingRequestEntityMapper bookingRequestEntityMapper;

    @Override
    public Optional<BookingRequest> findById(String id) {
        return jpaBookingRequestEntityRepository
                .findById(UUID.fromString(id))
                .map(bookingRequestEntityMapper::toDomain);
    }

    @Override
    public BookingRequest save(BookingRequest bookingRequest) {
        var bookingRequestEntity = bookingRequestEntityMapper.toEntity(bookingRequest);
        var savedBookingRequestEntity = jpaBookingRequestEntityRepository.save(bookingRequestEntity);
        return bookingRequestEntityMapper.toDomain(savedBookingRequestEntity);
    }


}
