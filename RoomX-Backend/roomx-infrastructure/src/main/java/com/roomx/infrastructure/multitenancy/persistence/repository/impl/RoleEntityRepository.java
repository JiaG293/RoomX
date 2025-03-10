package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoleEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoleEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleEntityRepository implements RoleRepository {

    private final JpaRoleEntityRepository jpaRoleEntityRepository;
    private final RoleEntityJpaMapper roleEntityJpaMapper;

    @Override
    public Optional<Role> findById(String roleId) {
        return jpaRoleEntityRepository.findById(roleId).map(roleEntityJpaMapper::toDomain);
    }

    @Override
    public void save(Role role) {
        jpaRoleEntityRepository.save(roleEntityJpaMapper.toEntity(role));
    }

    @Override
    public void delete(Role role) {
        jpaRoleEntityRepository.delete(roleEntityJpaMapper.toEntity(role));
    }

    @Override
    public void deleteById(String roleId) {
        jpaRoleEntityRepository.deleteById(roleId);
    }

    @Override
    public int findLevelByRole(String roleId) {
        return jpaRoleEntityRepository.findById(roleId).orElseThrow().getLevel();
    }

    @Override
    public List<Role> findAll() {
        return jpaRoleEntityRepository.findAll().stream().map(roleEntityJpaMapper::toDomain).toList();
    }
}
