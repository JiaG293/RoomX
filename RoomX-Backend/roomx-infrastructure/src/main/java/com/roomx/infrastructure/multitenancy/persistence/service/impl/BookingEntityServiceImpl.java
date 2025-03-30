package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.infrastructure.multitenancy.persistence.dto.BookingFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.PageableQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEntityServiceImpl implements PageableQueryService<BookingFilter, Booking> {
    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final BookingEntityMapper bookingEntityMapper;

    @Override
    public Page<Booking> filterPageBranchs(BookingFilter filter, Pageable pageable, boolean typeCompare) {
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                        new AbstractMap.SimpleEntry<>("id", filter.getId()),
                        new AbstractMap.SimpleEntry<>("bookingCode", filter.getBookingCode()),
                        new AbstractMap.SimpleEntry<>("bookingRequestId", filter.getBookingRequestId()),
                        new AbstractMap.SimpleEntry<>("roomId", filter.getRoomId()),
                        new AbstractMap.SimpleEntry<>("previousRoomId", filter.getPreviousRoomId()),
                        new AbstractMap.SimpleEntry<>("meetingStart", filter.getMeetingStart()),
                        new AbstractMap.SimpleEntry<>("meetingEnd", filter.getMeetingEnd()),
                        new AbstractMap.SimpleEntry<>("meetingDate", filter.getMeetingDate()),
                        new AbstractMap.SimpleEntry<>("totalPrice", filter.getTotalPrice()),
                        new AbstractMap.SimpleEntry<>("status", filter.getStatus()),
                        new AbstractMap.SimpleEntry<>("createdAt", filter.getCreatedAt()),
                        new AbstractMap.SimpleEntry<>("updatedAt", filter.getUpdatedAt())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();



        Specification<BookingEntity> spec = new GenericSpecification<>(filters);

        var bookingEntityPage = jpaBookingEntityRepository.findAll(spec, pageable);


        return bookingEntityPage.map(bookingEntityMapper::toDomain);
    }
}
