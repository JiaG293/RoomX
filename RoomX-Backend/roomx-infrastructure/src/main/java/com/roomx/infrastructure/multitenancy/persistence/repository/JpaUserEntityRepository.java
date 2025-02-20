package com.roomx.infrastructure.multitenancy.persistence.repository;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserEntityRepository extends JpaRepository<UserEntity, String >, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByEmployeeId(String employeeId);

    Optional<UserEntity> findByEmail(String email);
}
