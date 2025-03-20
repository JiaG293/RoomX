package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaBranchEntityRepository extends JpaRepository<BranchEntity, UUID>, JpaSpecificationExecutor<BranchEntity> {

    boolean existsByBranchCodeAndStatus(String branchCode, String status);

    List<BranchEntity> findAllByNameOrBranchCode(String name, String branchCode);

    Optional<BranchEntity> findByIdAndStatus(UUID id, String status);

    List<BranchEntity> findAllByStatus(String status);
}
