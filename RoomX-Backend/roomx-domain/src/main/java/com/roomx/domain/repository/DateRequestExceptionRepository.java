package com.roomx.domain.repository;

import com.roomx.domain.model.entity.DateRequestException;

import java.util.List;
import java.util.Optional;

public interface DateRequestExceptionRepository {
    Optional<DateRequestException> findById(String id);

    Optional<DateRequestException> findByBookingRequestId(String bookingRequestId);

    List<DateRequestException> findAllByBookingRequestId(String bookingRequestId);

    DateRequestException save(DateRequestException dateRequestException);

    List<DateRequestException> saveAll(List<DateRequestException> dateRequestExceptions);

}
