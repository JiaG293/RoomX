package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.Recurrence;
import com.roomx.domain.repository.RecurrenceRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RecurrenceEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRecurrenceEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RecurrenceEntityRepository implements RecurrenceRepository {
    private final JpaRecurrenceEntityRepository jpaRecurrenceEntityRepository;
    private final RecurrenceEntityMapper recurrenceEntityMapper;

    @Override
    public Optional<Recurrence> findById(String id) {
        return jpaRecurrenceEntityRepository
                .findById(UUID.fromString(id))
                .map(recurrenceEntityMapper::toDomain);
    }

    @Override
    public Recurrence save(Recurrence recurrence) {
        var recurrenceEntity = recurrenceEntityMapper.toEntity(recurrence);
        var savedRecurrenceEntity = jpaRecurrenceEntityRepository.save(recurrenceEntity);
        return recurrenceEntityMapper.toDomain(savedRecurrenceEntity);
    }
}
