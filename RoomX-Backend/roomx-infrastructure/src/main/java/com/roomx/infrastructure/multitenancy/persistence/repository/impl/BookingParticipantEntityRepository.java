package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.repository.BookingParticipantRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingParticipantEntityIdMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingParticipantEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBookingParticipantEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookingParticipantEntityRepository implements BookingParticipantRepository {
    private final JpaBookingParticipantEntityRepository jpaBookingParticipantEntityRepository;
    private final BookingParticipantEntityMapper bookingParticipantEntityMapper;
    @Override
    public BookingParticipant save(BookingParticipant bookingParticipant) {
        var bookingParticipantEntity = bookingParticipantEntityMapper.toEntity(bookingParticipant);
        var savedBookingParticipantEntity = jpaBookingParticipantEntityRepository.save(bookingParticipantEntity);
        return bookingParticipantEntityMapper.toDomain(savedBookingParticipantEntity);
    }

    @Override
    public List<BookingParticipant> saveAll(List<BookingParticipant> bookingParticipants) {
        var bookingParticipantEntityList = bookingParticipants.stream().map(bookingParticipantEntityMapper::toEntity).toList();
        var savedBookingParticipantEntityList = jpaBookingParticipantEntityRepository.saveAll(bookingParticipantEntityList);
        return savedBookingParticipantEntityList.stream().map(bookingParticipantEntityMapper::toDomain).toList();
    }

    @Override
    public boolean checkExistsByBookingParticipantId(BookingParticipantId id) {
        return false;
    }

    @Override
    public List<BookingParticipant> findAllBookingId(String bookingId) {
        return jpaBookingParticipantEntityRepository
                .findAllByBookingId(UUID.fromString(bookingId))
                .stream().map(bookingParticipantEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<BookingParticipant> findAllByUserId(String useId) {
        return  jpaBookingParticipantEntityRepository
                .findAllByUserId(UUID.fromString(useId))
                .stream().map(bookingParticipantEntityMapper::toDomain)
                .toList();
    }


}
