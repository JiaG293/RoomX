package com.roomx.infrastructure.multitenancy.persistence.model.base;


import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*public class GenericSpecification<T> implements Specification<T> {
    private final List<SearchCriteria> criteriaList;

    public GenericSpecification(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> andPredicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            String key = criteria.getKey();
            Object value = criteria.getValue();
            String operation = Optional.ofNullable(criteria.getOperation()).orElse("=").toLowerCase();

            if (key == null || !root.getModel().getAttributes().stream().anyMatch(a -> a.getName().equals(key))) {
                continue; // Bỏ qua nếu key không hợp lệ
            }

            Predicate condition = switch (operation) {
                case "=" -> value == null ? criteriaBuilder.isNull(root.get(key)) : criteriaBuilder.equal(root.get(key), value);
                case "!=" -> value == null ? criteriaBuilder.isNotNull(root.get(key)) : criteriaBuilder.notEqual(root.get(key), value);
                case ">" -> criteriaBuilder.greaterThan(root.get(key).as(String.class), value.toString());
                case "<" -> criteriaBuilder.lessThan(root.get(key).as(String.class), value.toString());
                case ">=" -> criteriaBuilder.greaterThanOrEqualTo(root.get(key).as(String.class), value.toString());
                case "<=" -> criteriaBuilder.lessThanOrEqualTo(root.get(key).as(String.class), value.toString());
                case "like", "contains", "%" -> value == null ? null : criteriaBuilder.like(root.get(key), "%" + value + "%");
                case "in" -> (value instanceof List<?>) ? root.get(key).in((List<?>) value) : null;
                default -> null;
            };

            if (condition != null) {
                if (criteria.isOrCondition()) {
                    orPredicates.add(condition);
                } else {
                    andPredicates.add(condition);
                }
            }
        }

        // Nếu không có điều kiện, trả về toàn bộ danh sách
        if (andPredicates.isEmpty() && orPredicates.isEmpty()) {
            return criteriaBuilder.conjunction(); // Tương đương với WHERE 1=1
        }

        Predicate finalAndPredicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
        Predicate finalOrPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));

        return orPredicates.isEmpty() ? finalAndPredicate : criteriaBuilder.or(finalAndPredicate, finalOrPredicate);
    }

}
        */


public class GenericSpecification<T> implements Specification<T> {
    private final List<SearchCriteria> criteriaList;

    public GenericSpecification(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> andPredicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            String key = criteria.getKey();
            Object value = criteria.getValue();
            String operation = Optional.ofNullable(criteria.getOperation()).orElse("=").toLowerCase();

            if (key == null || value == null) {
                continue;
            }

            Path<?> path;
            if (key.contains(".")) {
                String[] parts = key.split("\\.");
                Join<Object, Object> join = root.join(parts[0], JoinType.LEFT);
                path = join.get(parts[1]);
            } else {
                path = root.get(key);
            }

            Predicate condition = switch (operation) {
                case "=" -> criteriaBuilder.equal(path, value);
                case "!=" -> criteriaBuilder.notEqual(path, value);
                case ">" -> criteriaBuilder.greaterThan(path.as(String.class), value.toString());
                case "<" -> criteriaBuilder.lessThan(path.as(String.class), value.toString());
                case ">=" -> criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value.toString());
                case "<=" -> criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value.toString());
                case "like", "contains", "%" -> criteriaBuilder.like(path.as(String.class), "%" + value + "%");
                case "in" -> (value instanceof List<?>) ? path.in((List<?>) value) : null;
                case "between" -> {
                    if (value instanceof List<?> list && list.size() == 2) {
                        Object min = list.get(0);
                        Object max = list.get(1);

                        if (min instanceof Integer && max instanceof Integer) {
                            yield criteriaBuilder.between(root.get(key).as(Integer.class), (Integer) min, (Integer) max);
                        } else if (min instanceof BigDecimal && max instanceof BigDecimal) {
                            yield criteriaBuilder.between(root.get(key).as(BigDecimal.class), (BigDecimal) min, (BigDecimal) max);
                        } else if (min instanceof LocalDate && max instanceof LocalDate) {
                            yield criteriaBuilder.between(root.get(key).as(LocalDate.class), (LocalDate) min, (LocalDate) max);
                        }
                    }
                    yield null;
                }
                default -> null;
            };

            if (condition != null) {
                if (criteria.isOrCondition()) {
                    orPredicates.add(condition);
                } else {
                    andPredicates.add(condition);
                }
            }
        }

        Predicate finalAndPredicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
        Predicate finalOrPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));

        return orPredicates.isEmpty() ? finalAndPredicate : criteriaBuilder.or(finalAndPredicate, finalOrPredicate);
    }
}