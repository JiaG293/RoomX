package com.roomx.infrastructure.persistence.repository.specification;

import com.roomx.infrastructure.persistence.dto.PlaceFilter;
import com.roomx.infrastructure.persistence.model.entity.PlaceEntity;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Slf4j
public class PlaceSpecification {
    public static Specification<PlaceEntity> searchFilterPlace(PlaceFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();


            log.info("data la: {}", filter);
            // Lọc theo status
            if (StringUtils.hasText(filter.getStatus())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            // Lọc theo place type
            if (StringUtils.hasText(filter.getPlaceType())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("placeType"), filter.getPlaceType()));
            }

            // Tìm theo keyword
            if (StringUtils.hasText(filter.getKeyword())) {
                String keyword = "%" + filter.getKeyword().toLowerCase() + "%";

                List<String> searchFields = StringUtils.hasText(filter.getSearchBy()) ?
                        Arrays.asList(filter.getSearchBy().split(",")) :
                        List.of("id", "parentId", "branch", "name", "code");

                Map<String, Expression<String>> fieldMapping = new HashMap<>();
                fieldMapping.put("id", criteriaBuilder.toString(root.get("id")));
                fieldMapping.put("branch", criteriaBuilder.toString(root.get("branch")));
                fieldMapping.put("code", criteriaBuilder.lower(root.get("code")));
                fieldMapping.put("name", criteriaBuilder.lower(root.get("name")));

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
