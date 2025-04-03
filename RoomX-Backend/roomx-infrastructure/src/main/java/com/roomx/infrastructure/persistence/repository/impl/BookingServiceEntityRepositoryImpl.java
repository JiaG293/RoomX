package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.BookingService;
import com.roomx.domain.model.vo.BookingServiceId;
import com.roomx.domain.repository.BookingServiceRepository;
import com.roomx.infrastructure.persistence.mapper.BookingServiceEntityIdMapper;
import com.roomx.infrastructure.persistence.mapper.BookingServiceEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingServiceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookingServiceEntityRepositoryImpl implements BookingServiceRepository {
    private final BookingServiceEntityMapper bookingServiceEntityMapper;
    private final JpaBookingServiceEntityRepository jpaBookingServiceEntityRepository;
    private final BookingServiceEntityIdMapper bookingServiceEntityIdMapper;

    @Override
    public Optional<BookingService> findById(BookingServiceId bookingServiceId) {
        return jpaBookingServiceEntityRepository
                .findById(bookingServiceEntityIdMapper.toEntity(bookingServiceId))
                .map(bookingServiceEntityMapper::toDomain);
    }

    @Override
    public Optional<BookingService> findByBookingId(String bookingId) {
        return jpaBookingServiceEntityRepository
                .findByBookingId(UUID.fromString(bookingId))
                .map(bookingServiceEntityMapper::toDomain);
    }

    @Override
    public Optional<BookingService> findByServiceId(String serviceId) {
        return jpaBookingServiceEntityRepository
                .findByServiceId(UUID.fromString(serviceId))
                .map(bookingServiceEntityMapper::toDomain);
    }

    @Override
    public List<BookingService> findAllByBookingId(String bookingId) {
        return jpaBookingServiceEntityRepository
                .findAll().stream()
                .map(bookingServiceEntityMapper::toDomain).toList();
    }

    @Override
    public BookingService save(BookingService bookingService) {
        var bookingServiceEntity = bookingServiceEntityMapper.toEntity(bookingService);
        var savedBookingServiceEntity = jpaBookingServiceEntityRepository.save(bookingServiceEntity);
        return bookingServiceEntityMapper.toDomain(savedBookingServiceEntity);
    }

    @Override
    public List<BookingService> saveAll(List<BookingService> listBookingService) {
        var bookingServiceEntityList = listBookingService.stream().map(bookingServiceEntityMapper::toEntity).toList();
        var savedBookingServiceEntityList = jpaBookingServiceEntityRepository.saveAllAndFlush(bookingServiceEntityList);
        return savedBookingServiceEntityList.stream().map(bookingServiceEntityMapper::toDomain).toList();
    }
}
