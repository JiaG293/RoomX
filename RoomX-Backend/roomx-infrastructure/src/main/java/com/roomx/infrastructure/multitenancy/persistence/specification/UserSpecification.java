package com.roomx.infrastructure.multitenancy.persistence.specification;

import com.roomx.domain.model.enums.UserType;
import com.roomx.infrastructure.multitenancy.persistence.dto.UserFilter;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {
    public static Specification<UserEntity> filterUsers(UserFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + filter.getEmail() + "%"));
            }
            if (filter.getUserCode() != null && !filter.getUserCode().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("employeeId"), filter.getUserCode()));
            }
            if (filter.getUserType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userType"), UserType.fromDisplayName(filter.getUserType())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
