package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.infrastructure.persistence.dto.BookingFilter;
import com.roomx.infrastructure.persistence.dto.BookingGetFilter;
import com.roomx.infrastructure.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.persistence.mapper.BookingParticipantEntityMapper;
import com.roomx.infrastructure.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.persistence.model.dto.BookingDto;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.persistence.model.projection.BookingProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingParticipantEntityRepository;
import com.roomx.infrastructure.persistence.repository.specification.BookingRequestSpecification;
import com.roomx.infrastructure.persistence.repository.specification.BookingSpecification;
import com.roomx.infrastructure.persistence.service.BookingEntityService;
import com.roomx.infrastructure.persistence.service.PageableQueryService;
import com.roomx.shared.base.BookingListDto;
import com.roomx.shared.dto.booking.response.BookingMiniumResponse;
import com.roomx.shared.dto.booking.response.RoomBookingResponse;
import com.roomx.shared.dto.resource.response.PlaceNameResponse;
import com.roomx.shared.enums.BookingStatusType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEntityServiceImpl implements BookingEntityService {
    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final BookingEntityMapper bookingEntityMapper;
    private final JpaBookingParticipantEntityRepository jpaBookingParticipantEntityRepository;
    private final BookingParticipantEntityMapper bookingParticipantEntityMapper;

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public Page<Booking> filterSearchPageBooking(BookingFilter filter, Pageable pageable) {
        var spec = BookingSpecification.searchFilterBooking(filter);

        var bookingEntityPage = jpaBookingEntityRepository.findAll(spec, pageable);

        return bookingEntityPage.map(bookingEntityMapper::toDomain);
    }

    @Override
    public Page<Booking> filterSearchPageBookingWithUser(BookingGetFilter filter, String userId, Pageable pageable) {
        var spec = BookingSpecification.searchFilterBookingWithUser(filter, userId);

        var bookingEntityPage = jpaBookingEntityRepository.findAll(spec, pageable);

        return bookingEntityPage.map(bookingEntityMapper::toDomain);
    }

    @Override
    public Page<BookingMiniumResponse> findBookingsByTimeRangeAndUserId(LocalDate startDate, LocalDate endDate, String userId, Pageable pageable) {

        var result = jpaBookingEntityRepository
                .findAllByMeetingDateBetweenAndUserId(startDate, endDate, UUID.fromString(userId), pageable);

        var content = result.getContent().stream()
                .map(booking -> BookingMiniumResponse.builder()
                        .id(booking.getId())
                        .title(booking.getTitle())
                        .description(booking.getDescription())
                        .bookingCode(booking.getBookingCode())
                        .room(new RoomBookingResponse(booking.getId(), booking.getRoomCode(), booking.getRoomName()))
                        .floor(new PlaceNameResponse(booking.getFloorId(), booking.getFloorCode(), booking.getFloorName()))
                        .building(new PlaceNameResponse(booking.getBuildingId(), booking.getBuildingCode(), booking.getBuildingName()))
                        .branch(new PlaceNameResponse(booking.getBranchId(), booking.getBranchCode(), booking.getBranchName()))
                        .previousRoom(booking.getRoomPreviousId())
                        .meetingStart(booking.getMeetingStart())
                        .meetingEnd(booking.getMeetingEnd())
                        .meetingDate(booking.getMeetingDate())
                        .count(booking.getCount())
                        .status(booking.getStatus())
                        .createdAt(booking.getCreatedAt())
                        .updatedAt(booking.getUpdatedAt())
                        .build())
                .toList();

        return new PageImpl<>(content, pageable, result.getTotalElements());
    }

    @Override
    public Page<BookingMiniumResponse> findBookingsByTimeRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        var result = jpaBookingEntityRepository
                .findAllByMeetingDateBetween(startDate, endDate, pageable);

        var content = result.getContent().stream()
                .map(booking -> BookingMiniumResponse.builder()
                        .id(booking.getId())
                        .title(booking.getTitle())
                        .description(booking.getDescription())
                        .bookingCode(booking.getBookingCode())
                        .room(new RoomBookingResponse(booking.getId(), booking.getRoomCode(), booking.getRoomName()))
                        .floor(new PlaceNameResponse(booking.getFloorId(), booking.getFloorCode(), booking.getFloorName()))
                        .building(new PlaceNameResponse(booking.getBuildingId(), booking.getBuildingCode(), booking.getBuildingName()))
                        .branch(new PlaceNameResponse(booking.getBranchId(), booking.getBranchCode(), booking.getBranchName()))
                        .previousRoom(booking.getRoomPreviousId())
                        .meetingStart(booking.getMeetingStart())
                        .meetingEnd(booking.getMeetingEnd())
                        .meetingDate(booking.getMeetingDate())
                        .count(booking.getCount())
                        .status(booking.getStatus())
                        .createdAt(booking.getCreatedAt())
                        .updatedAt(booking.getUpdatedAt())
                        .build())
                .toList();

        return new PageImpl<>(content, pageable, result.getTotalElements());
    }




}
