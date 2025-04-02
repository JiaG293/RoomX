package com.roomx.infrastructure.persistence.repository.specification;

import com.roomx.shared.base.filter.BranchFilter;
import com.roomx.infrastructure.persistence.model.entity.BranchEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;


public class BranchSpecification {
    public static Specification<BranchEntity> searchFilterBranch(BranchFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Filter theo status
            if (StringUtils.hasText(filter.getStatus())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            // Filter theo khoảng thời gian
            Instant fromDate = filter.getFromDate();
            Instant toDate = filter.getToDate();

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

                // Danh sách field default
                List<String> searchFields = StringUtils.hasText(filter.getSearchBy()) ?
                        Arrays.asList(filter.getSearchBy().split(",")) :
                        List.of("name", "branchcode", "phoneNumber", "createAt", "updatedAt", "email", "address", "id"); // Mặc định nếu không có searchBy

                // Ánh xạ
                Map<String, Expression<String>> fieldMapping = new HashMap<>();
                fieldMapping.put("name", criteriaBuilder.lower(root.get("name")));
                fieldMapping.put("branchCode", criteriaBuilder.lower(root.get("branchCode")));
                fieldMapping.put("email", criteriaBuilder.lower(root.get("email")));
                fieldMapping.put("address", criteriaBuilder.lower(root.get("address")));
                fieldMapping.put("phoneNumber", criteriaBuilder.lower(root.get("phoneNumber")));
                fieldMapping.put("id", criteriaBuilder.toString(root.get("id")));

                // Tạo searchPredicates
                List<Predicate> searchPredicates = searchFields.stream()
                        .map(String::trim)
                        .map(String::toLowerCase)
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
