package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.BookingEquipment;
import com.roomx.domain.model.vo.BookingEquipmentId;
import com.roomx.domain.repository.BookingEquipmentRepository;
import com.roomx.infrastructure.persistence.mapper.BookingEquipmentEntityIdMapper;
import com.roomx.infrastructure.persistence.mapper.BookingEquipmentEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingEquipmentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookingEquipmentEntityRepository implements BookingEquipmentRepository {
    private final BookingEquipmentEntityMapper bookingEquipmentEntityMapper;
    private final BookingEquipmentEntityIdMapper bookingEquipmentEntityIdMapper;
    private final JpaBookingEquipmentEntityRepository jpaBookingEquipmentEntityRepository;

    @Override
    public Optional<BookingEquipment> findById(BookingEquipmentId bookingEquipmentId) {
        return jpaBookingEquipmentEntityRepository
                .findById(bookingEquipmentEntityIdMapper.toEntity(bookingEquipmentId))
                .map(bookingEquipmentEntityMapper::toDomain);
    }

    @Override
    public Optional<BookingEquipment> findByBookingId(String bookingId) {
        return jpaBookingEquipmentEntityRepository
                .findByBookingId(UUID.fromString(bookingId))
                .map(bookingEquipmentEntityMapper::toDomain);
    }

    @Override
    public Optional<BookingEquipment> findByEquipmentId(String equipmentId) {
        return jpaBookingEquipmentEntityRepository
                .findByEquipmentId(UUID.fromString(equipmentId))
                .map(bookingEquipmentEntityMapper::toDomain);
    }

    @Override
    public List<BookingEquipment> findAllByBookingId(String bookingId) {
        return jpaBookingEquipmentEntityRepository
                .findAllByBookingId(UUID.fromString(bookingId))
                .stream().map(bookingEquipmentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public BookingEquipment save(BookingEquipment bookingEquipment) {
        var bookingEquipmentEntity = bookingEquipmentEntityMapper.toEntity(bookingEquipment);
        return bookingEquipmentEntityMapper.toDomain(jpaBookingEquipmentEntityRepository.save(bookingEquipmentEntity));
    }

    @Override
    public List<BookingEquipment> saveAll(List<BookingEquipment> listBookingEquipment) {
        var bookingEquipmentEntityList = listBookingEquipment.stream().map(bookingEquipmentEntityMapper::toEntity).toList();
        var savedBookingEquipmentEntityList = jpaBookingEquipmentEntityRepository.saveAllAndFlush(bookingEquipmentEntityList);
        return savedBookingEquipmentEntityList.stream().map(bookingEquipmentEntityMapper::toDomain).toList();
    }
}
