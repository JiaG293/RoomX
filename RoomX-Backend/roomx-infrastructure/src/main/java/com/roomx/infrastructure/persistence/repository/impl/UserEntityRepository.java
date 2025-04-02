package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.persistence.mapper.UserEntityMapper;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;
import com.roomx.infrastructure.persistence.repository.jpa.JpaUserEntityRepository;
import com.roomx.infrastructure.persistence.repository.specification.UserEntitySpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserEntityRepository implements UserRepository, UserEntitySpecRepository {

    private final JpaUserEntityRepository jpaUserEntityRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findById(UUID id, boolean enabled) {
        return jpaUserEntityRepository.findByIdAndEnable(id, true).map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByUserCode(String userCode) {
        return jpaUserEntityRepository.findByUserCode(userCode).map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserEntityRepository.findByEmail(email).map(userEntityMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserEntityRepository.findAll().stream().map(userEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        jpaUserEntityRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void delete(String id) {
        jpaUserEntityRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<User> findAllUserWithRole(String roleName) {
        return jpaUserEntityRepository
                .findAllByRoleId(roleName)
                .stream().map(userEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email, boolean enabled) {
        return jpaUserEntityRepository.findByEmailAndEnable(email, enabled)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, boolean enabled) {
        return jpaUserEntityRepository
                .findByEmailAndEnable(email, enabled)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<UUID> findByEmailCustom(String email) {

        return jpaUserEntityRepository.findByEmailCustom(email);
    }

    @Override
    public Page<UserEntity> findAll(Specification specification, Pageable pageable) {
        return jpaUserEntityRepository.findAll(specification, pageable);
    }

}
