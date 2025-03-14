package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.repository.UserRoleRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoleEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRoleEntityRepository implements UserRoleRepository {
    private final JpaRoleEntityRepository jpaRoleEntityRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<User> findUserWithRole() {
        return Optional.empty();
    }

    @Override
    public List<Role> findRolesByUserId(UUID userId) {
        return jpaRoleEntityRepository.findRolesNotAssignedUser(userId).stream().map(roleEntityMapper::toDomain).toList();
    }

}
