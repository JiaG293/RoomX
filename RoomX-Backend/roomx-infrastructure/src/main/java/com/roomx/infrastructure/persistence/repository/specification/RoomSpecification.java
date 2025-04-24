package com.roomx.infrastructure.persistence.repository.specification;

import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.persistence.model.entity.EquipmentPriceHistoryEntity;
import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Slf4j
public class RoomSpecification {
    public static Specification<RoomEntity> searchFilterRoom(RoomFilter filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Join roomClass → priceHistory (nếu cần lọc giá)
            Join<Object, Object> roomClassJoin = root.join("roomClass", JoinType.LEFT);

            if (filter.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), filter.getStatus()));
            }

            if (StringUtils.hasText(filter.getRoomCode())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("roomCode")), "%" + filter.getRoomCode().toLowerCase() + "%"));
            }

            if (filter.getStartPrice() != null || filter.getEndPrice() != null) {
                // Giả sử có: roomClass.priceHistories
                Join<Object, Object> priceHistoryJoin = roomClassJoin.join("priceHistories", JoinType.LEFT);

                Predicate pricePredicate = cb.conjunction();
                if (filter.getStartPrice() != null) {
                    pricePredicate = cb.and(pricePredicate, cb.greaterThanOrEqualTo(priceHistoryJoin.get("basePrice"), filter.getStartPrice()));
                }
                if (filter.getEndPrice() != null) {
                    pricePredicate = cb.and(pricePredicate, cb.lessThanOrEqualTo(priceHistoryJoin.get("basePrice"), filter.getEndPrice()));
                }

                predicate = cb.and(predicate, pricePredicate);
            }

            if (StringUtils.hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";
                Predicate keywordPredicate = cb.or(
                        cb.like(cb.lower(root.get("roomCode")), keyword),
                        cb.like(cb.lower(root.get("description")), keyword),
                        cb.like(cb.lower(root.get("status")), keyword)
                );
                predicate = cb.and(predicate, keywordPredicate);
            }

            return predicate;
        };
    }
}
