package com.roomx.infrastructure.multitenancy.persistence.repository.specification;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserEntitySpecRepository {
    Page<UserEntity> findAll(Specification specification, Pageable pageable);
}
