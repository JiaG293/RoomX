package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.Permission;
import com.roomx.domain.repository.PermissionRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.PermissionEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.JpaPermissionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PermissionEntityRepository implements PermissionRepository {
    private final JpaPermissionEntityRepository jpaPermissionEntityRepository;
    private final PermissionEntityJpaMapper permissionEntityJpaMapper;


    @Override
    public Optional<Permission> findById(String id) {
        return jpaPermissionEntityRepository.findById(id).map(permissionEntityJpaMapper::toDomain);
    }

    @Override
    public void save(Permission permission) {
        jpaPermissionEntityRepository.save(permissionEntityJpaMapper.toEntity(permission));
    }

    @Override
    public void delete(Permission permission) {
        jpaPermissionEntityRepository.delete(permissionEntityJpaMapper.toEntity(permission));
    }

    @Override
    public void deleteById(String id) {
        jpaPermissionEntityRepository.deleteById(id);
    }
}
