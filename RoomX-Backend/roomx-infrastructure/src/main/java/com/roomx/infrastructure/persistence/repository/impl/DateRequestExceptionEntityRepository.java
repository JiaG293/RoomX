package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.domain.repository.DateRequestExceptionRepository;
import com.roomx.infrastructure.persistence.mapper.DateRequestExceptionEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaDateRequestExceptionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateRequestExceptionEntityRepository implements DateRequestExceptionRepository {
    private final JpaDateRequestExceptionEntityRepository jpaDateRequestExceptionEntityRepository;
    private final DateRequestExceptionEntityMapper dateRequestExceptionEntityMapper;

    @Override
    public Optional<DateRequestException> findById(String id) {
        return jpaDateRequestExceptionEntityRepository
                .findById(UUID.fromString(id))
                .map(dateRequestExceptionEntityMapper::toDomain);
    }

    @Override
    public Optional<DateRequestException> findByBookingRequestId(String bookingRequestId) {
        return jpaDateRequestExceptionEntityRepository
                .findByBookingRequestId(UUID.fromString(bookingRequestId))
                .map(dateRequestExceptionEntityMapper::toDomain);
    }

    @Override
    public List<DateRequestException> findAllByBookingRequestId(String bookingRequestId) {
        return jpaDateRequestExceptionEntityRepository
                .findAllByBookingRequestId(UUID.fromString(bookingRequestId))
                .stream().map(dateRequestExceptionEntityMapper::toDomain)
                .toList();
    }

    @Override
    public DateRequestException save(DateRequestException dateRequestException) {
        var entityDateRequestException = dateRequestExceptionEntityMapper.toEntity(dateRequestException);
        var savedDateRequestException = jpaDateRequestExceptionEntityRepository.save(entityDateRequestException);
        return dateRequestExceptionEntityMapper.toDomain(savedDateRequestException);
    }

    @Override
    public List<DateRequestException> saveAll(List<DateRequestException> dateRequestExceptions) {
        var entityDateRequestExceptions = dateRequestExceptions.stream().map(dateRequestExceptionEntityMapper::toEntity).toList();
        var savedDateRequestExceptions = jpaDateRequestExceptionEntityRepository.saveAll(entityDateRequestExceptions);
        return savedDateRequestExceptions
                .stream().map(dateRequestExceptionEntityMapper::toDomain)
                .toList();
    }
}
