package com.roomx.infrastructure.persistence.service;

import com.roomx.infrastructure.persistence.dto.GroupFilter;
import com.roomx.infrastructure.persistence.dto.UserFilter;
import com.roomx.infrastructure.persistence.model.projection.GroupProjection;
import com.roomx.infrastructure.persistence.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserEntityService {
    Page<UserProjection> filterSearchGroup(UserFilter userFilter, Pageable pageable);
}
