package com.roomx.infrastructure.multitenancy.persistence.repository.specification;

import com.roomx.infrastructure.multitenancy.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServicePriceHistoryEntity;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Slf4j
public class ServiceSpecification {
    public static Specification<ServiceEntity> searchFilterService(ServiceFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            Join<ServiceEntity, ServicePriceHistoryEntity> priceHistoryJoin = root.join("priceHistories", JoinType.LEFT);
            Predicate activePrice = criteriaBuilder.isTrue(priceHistoryJoin.get("active"));
            Subquery<Instant> maxValidFromSubquery = query.subquery(Instant.class);
            Root<ServicePriceHistoryEntity> subRoot = maxValidFromSubquery.from(ServicePriceHistoryEntity.class);
            maxValidFromSubquery.select(
                    criteriaBuilder.greatest(subRoot.<Instant>get("validFrom"))
            ).where(
                    criteriaBuilder.equal(subRoot.get("service"), root),
                    criteriaBuilder.isTrue(subRoot.get("active"))
            );

            Predicate latestPrice = criteriaBuilder.equal(priceHistoryJoin.get("validFrom"), maxValidFromSubquery);
            predicate = criteriaBuilder.and(predicate, activePrice, latestPrice);


            if (StringUtils.hasText(filter.getStatus())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

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

            // Tìm theo keyword
            if (StringUtils.hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";

                List<String> searchFields = StringUtils.hasText(filter.getSearchBy()) ?
                        Arrays.asList(filter.getSearchBy().split(",")) :
                        List.of("id", "serviceCode", "name", "note", "description");

                Map<String, Expression<String>> fieldMapping = new HashMap<>();
                fieldMapping.put("id", criteriaBuilder.toString(root.get("id")));
                fieldMapping.put("serviceCode", criteriaBuilder.lower(root.get("serviceCode")));
                fieldMapping.put("name", criteriaBuilder.lower(root.get("name")));
                fieldMapping.put("note", criteriaBuilder.lower(root.get("note")));
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
