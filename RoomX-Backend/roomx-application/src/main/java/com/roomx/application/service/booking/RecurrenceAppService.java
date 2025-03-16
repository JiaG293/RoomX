package com.roomx.application.service.booking;

import com.roomx.application.mapper.RecurrenceAppMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRecurrenceEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecurrenceAppService {
    private final JpaRecurrenceEntityRepository jpaRecurrenceEntityRepository;
    private final RecurrenceAppMapper recurrenceAppMapper;

}
