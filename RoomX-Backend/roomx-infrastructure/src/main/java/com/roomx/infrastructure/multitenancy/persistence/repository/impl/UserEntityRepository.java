package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.employee.model.User;
import com.roomx.domain.employee.repository.UserRepository;
import com.roomx.infrastructure.multitenancy.persistence.dto.UserFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.UserEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.JpaUserEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.UserEntityQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserEntityRepository implements UserRepository, UserEntityQueryRepository {

    private final JpaUserEntityRepository jpaUserEntityRepository;
    private final UserEntityJpaMapper userEntityJpaMapper;

    @Override
    public Optional<User> findById(String id) {
        return jpaUserEntityRepository.findById(id).map(userEntityJpaMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmployeeId(String employeeId) {
        return jpaUserEntityRepository.findByEmployeeId(employeeId).map(userEntityJpaMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserEntityRepository.findByEmail(email).map(userEntityJpaMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserEntityRepository.findAll().stream().map(userEntityJpaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        jpaUserEntityRepository.save(userEntityJpaMapper.toEntity(user));
    }

    @Override
    public void delete(String id) {
        jpaUserEntityRepository.deleteById(id);
    }

    @Override
    public Page<UserEntity> findAll(Specification specification, Pageable pageable) {
        return jpaUserEntityRepository.findAll(specification, pageable);
    }
}
