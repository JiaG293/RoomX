package com.roomx.infrastructure.persistence.repository.specification;

import com.roomx.infrastructure.persistence.dto.BookingFilter;
import com.roomx.infrastructure.persistence.dto.BookingGetFilter;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.shared.enums.ApprovalStatusType;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class BookingSpecification {
    public static Specification<BookingEntity> searchFilterBooking(BookingFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();


            log.info("data la: {}", filter);
            // Lọc theo status
            if (StringUtils.hasText(filter.getStatus())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            // Lọc theo roomId
            if (StringUtils.hasText(filter.getRoomId())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("roomId"), filter.getRoomId()));
            }

            // Lọc theo khoảng thời gian họp
            if (filter.getFromMeetingDate() != null) {
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder
                                .greaterThanOrEqualTo(root.get("meetingDate"), filter.getFromMeetingDate()));
            }
            if (filter.getToMeetingDate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("meetingDate"), filter.getToMeetingDate()));
            }

            // Lọc theo khoảng thời gian từ LocalTime
            if (filter.getFromTime() != null) {
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder
                                .greaterThanOrEqualTo(root.get("meetingStart"), filter.getFromTime()));
            }
            if (filter.getToTime() != null) {
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder
                                .lessThanOrEqualTo(root.get("meetingEnd"), filter.getToTime()));
            }

            // Lọc theo khoảng giá
            if (filter.getFromTotalPrice() != null) {
                predicate = criteriaBuilder
                        .and(predicate, criteriaBuilder
                                .greaterThanOrEqualTo(root.get("totalPrice"), filter.getFromTotalPrice()));
            }
            if (filter.getToTotalPrice() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder
                        .lessThanOrEqualTo(root.get("totalPrice"), filter.getToTotalPrice()));
            }

            // Lọc theo timestamp
            if (filter.getFromTimestamp() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder
                        .greaterThanOrEqualTo(root.get("createdAt"), filter.getFromTimestamp()));
            }
            if (filter.getToTimestamp() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder
                        .lessThanOrEqualTo(root.get("updatedAt"), filter.getToTimestamp()));
            }


            // Tìm theo keyword
            if (StringUtils.hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";

                List<String> searchFields = StringUtils.hasText(filter.getSearchBy()) ?
                        Arrays.asList(filter.getSearchBy().split(",")) :
                        List.of("id", "room", "title", "bookingCode", "bookingRequest");

                Map<String, Expression<String>> fieldMapping = new HashMap<>();
                fieldMapping.put("id", criteriaBuilder.toString(root.get("id")));
                fieldMapping.put("room", criteriaBuilder.toString(root.get("room")));
                fieldMapping.put("title", criteriaBuilder.lower(root.get("title")));
                fieldMapping.put("bookingCode", criteriaBuilder.lower(root.get("bookingCode")));
                fieldMapping.put("bookingRequest", criteriaBuilder.toString(root.get("bookingRequest")));

                List<Predicate> searchPredicates = searchFields.stream()
                        .map(String::trim)
                        .map(fieldMapping::get)
                        .filter(Objects::nonNull)
                        .map(expression -> criteriaBuilder.like(expression, keyword))
                        .toList();

                if (!searchPredicates.isEmpty()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
                }
            }

            return predicate;
        };
    }

    public static Specification<BookingEntity> searchFilterBookingWithUser(BookingGetFilter filter, String userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            LocalDate today = LocalDate.now();

            int currentYear = today.getYear();
            int selectedYear = (filter.getYear() != null && filter.getYear() > 0) ? filter.getYear() : currentYear;
            int selectedMonth = (filter.getMonth() != null && filter.getMonth() >= 1 && filter.getMonth() <= 12) ? filter.getMonth() : today.getMonthValue();

            LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            ZoneId zoneId = ZoneId.systemDefault();
            Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
            Instant endInstant = endDate.atTime(LocalTime.MAX).atZone(zoneId).toInstant();

            predicates.add(root.get("status").in(ApprovalStatusType.getListCanApproval()));
            predicates.add(criteriaBuilder.between(root.get("bookingRequest").get("time"), startInstant, endInstant));

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("requester").get("id"), userId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
