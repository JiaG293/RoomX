package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.infrastructure.persistence.dto.UserFilter;
import com.roomx.infrastructure.persistence.model.projection.UserProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaUserEntityRepository;
import com.roomx.infrastructure.persistence.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final JpaUserEntityRepository jpaUserEntityRepository;

    @Override
    public Page<UserProjection> filterSearchGroup(UserFilter filter, Pageable pageable) {
        return jpaUserEntityRepository
                .findUserWithFilters(
                        filter.getKeyword(),
                        filter.getSearchBy(),
                        filter.getBranchId(),
                        filter.getGroupId(),
                        filter.getUserType(),
                        filter.getEnabled(),
                        filter.getRoles(),
                        pageable
                );
    }
}
