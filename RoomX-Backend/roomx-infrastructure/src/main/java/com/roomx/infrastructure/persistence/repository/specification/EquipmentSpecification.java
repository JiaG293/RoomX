package com.roomx.infrastructure.persistence.repository.specification;

import com.roomx.infrastructure.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.persistence.model.entity.EquipmentPriceHistoryEntity;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Slf4j
public class EquipmentSpecification {

    public static Specification<EquipmentEntity> searchFilterEquipment(EquipmentFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<EquipmentEntity, EquipmentPriceHistoryEntity> priceHistoryJoin = root.join("priceHistories", JoinType.LEFT);
            Predicate activePrice = criteriaBuilder.isTrue(priceHistoryJoin.get("active"));
            Subquery<Instant> maxValidFromSubquery = query.subquery(Instant.class);
            Root<EquipmentPriceHistoryEntity> subRoot = maxValidFromSubquery.from(EquipmentPriceHistoryEntity.class);
            maxValidFromSubquery.select(
                    criteriaBuilder.greatest(subRoot.<Instant>get("validFrom"))
            ).where(
                    criteriaBuilder.equal(subRoot.get("equipment"), root),
                    criteriaBuilder.isTrue(subRoot.get("active"))
            );

            Predicate latestPrice = criteriaBuilder.equal(priceHistoryJoin.get("validFrom"), maxValidFromSubquery);
            predicate = criteriaBuilder.and(predicate, activePrice, latestPrice);

            // Lọc theo status
            if (StringUtils.hasText(filter.getStatus())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            // Lọc theo khoảng giá
            BigDecimal minPrice = filter.getFromPrice();
            BigDecimal maxPrice = filter.getToPrice();
            if (minPrice != null || maxPrice != null) {
                Predicate pricePredicate = null;

                if (minPrice != null) {
                    pricePredicate = criteriaBuilder.greaterThanOrEqualTo(priceHistoryJoin.get("unitPrice"), minPrice);
                }
                if (maxPrice != null) {
                    Predicate maxPredicate = criteriaBuilder.lessThanOrEqualTo(priceHistoryJoin.get("unitPrice"), maxPrice);
                    pricePredicate = pricePredicate == null ? maxPredicate : criteriaBuilder.and(pricePredicate, maxPredicate);
                }

                predicate = criteriaBuilder.and(predicate, pricePredicate);
            }

            // Lọc theo ngày
            Instant fromDate = filter.getValidPriceFrom();
            Instant toDate = filter.getValidPriceEnd();
            if (fromDate != null || toDate != null) {
                Predicate createdAtPredicate = null;
                Predicate updatedAtPredicate = null;

                if (fromDate != null) {
                    createdAtPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate);
                    updatedAtPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), fromDate);
                }
                if (toDate != null) {
                    Predicate createdTo = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate);
                    Predicate updatedTo = criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), toDate);
                    createdAtPredicate = createdAtPredicate == null ? createdTo : criteriaBuilder.and(createdAtPredicate, createdTo);
                    updatedAtPredicate = updatedAtPredicate == null ? updatedTo : criteriaBuilder.and(updatedAtPredicate, updatedTo);
                }

                if (createdAtPredicate != null && updatedAtPredicate != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(createdAtPredicate, updatedAtPredicate));
                } else if (createdAtPredicate != null) {
                    predicate = criteriaBuilder.and(predicate, createdAtPredicate);
                } else if (updatedAtPredicate != null) {
                    predicate = criteriaBuilder.and(predicate, updatedAtPredicate);
                }
            }

            // Lọc theo brand
            if (StringUtils.hasText(filter.getBrand())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("brand"), filter.getBrand()));
            }

            // Lọc theo keyword
            if (StringUtils.hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";

                List<String> searchFields = StringUtils.hasText(filter.getSearchBy()) ?
                        Arrays.asList(filter.getSearchBy().split(",")) :
                        List.of("id", "equipmentCode", "name", "brand", "description");

                Map<String, Expression<String>> fieldMapping = new HashMap<>();
                fieldMapping.put("id", criteriaBuilder.toString(root.get("id")));
                fieldMapping.put("equipmentCode", criteriaBuilder.lower(root.get("equipmentCode")));
                fieldMapping.put("name", criteriaBuilder.lower(root.get("name")));
                fieldMapping.put("brand", criteriaBuilder.lower(root.get("brand")));
                fieldMapping.put("description", criteriaBuilder.lower(root.get("description")));

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

}
