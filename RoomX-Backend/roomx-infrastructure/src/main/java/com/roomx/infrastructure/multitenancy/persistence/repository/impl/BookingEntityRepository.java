package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.dto.BookingDto;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.repository.BookingRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.shared.enums.BookingStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookingEntityRepository implements BookingRepository {
    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final BookingEntityMapper bookingEntityMapper;

    @Override
    public Optional<Booking> findById(String bookingId) {
        return jpaBookingEntityRepository
                .findById(UUID.fromString(bookingId))
                .map(bookingEntityMapper::toDomain);
    }

    @Override
    public Optional<Booking> findByIdAndStatus(String bookingId, String status) {
        return jpaBookingEntityRepository
                .findByIdAndStatus(UUID.fromString(bookingId), status)
                .map(bookingEntityMapper::toDomain);
    }

    @Override
    public List<Booking> findAllByStatus(String status) {
        return jpaBookingEntityRepository
                .findAllByStatus(status)
                .stream().map(bookingEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Booking save(Booking booking) {
        var bookingEntity = bookingEntityMapper.toEntity(booking);
        var savedBookingEntity = jpaBookingEntityRepository.save(bookingEntity);
        return bookingEntityMapper.toDomain(savedBookingEntity);
    }

    @Override
    public List<Booking> save(List<Booking> bookings) {
        var bookingEntityList = bookings.stream()
                .map(bookingEntityMapper::toEntity)
                .collect(Collectors.toList());
        var savedBookingEntityList = jpaBookingEntityRepository.saveAll(bookingEntityList);
        return savedBookingEntityList.stream()
                .map(bookingEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

   /* @Override
    public List<Booking> findAllByRoomIdsAndMeetingDate(List<String> roomIds, LocalDate date) {
        var roomIdsUUID = roomIds.stream().map(UUID::fromString).toList();
        return jpaBookingEntityRepository
                .findAllByRoomIdsAndMeetingDate(roomIdsUUID, date).stream()
                .map(bookingEntityMapper::toDomain).toList();
    }*/

    @Override
    public List<Booking> findAllByMeetingDateAndRoomId(LocalDate date, String roomId) {
        return jpaBookingEntityRepository
                .findAllByMeetingDateAndRoomId(date, UUID.fromString(roomId))
                .stream().map(bookingEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Booking> findAllByMeetingDate(LocalDate date) {
        return jpaBookingEntityRepository
                .findAllByMeetingDate(date).stream()
                .map(bookingEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Booking> findAllByMeetingDateAndContainsStatus(LocalDate date, List<String> listAccept) {
        return jpaBookingEntityRepository
                .findAllByMeetingDateAndStatusIn(date, listAccept)
                .stream().map(bookingEntityMapper::toDomain)
                .toList();
    }

}
