package com.roomx.domain.repository;

import com.roomx.domain.model.entity.Recurrence;

import java.util.Optional;

public interface RecurrenceRepository {
    Optional<Recurrence> findById(String id);
    Recurrence save(Recurrence recurrence);
}
