package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaBranchEntityRepository extends JpaRepository<BranchEntity, UUID>, JpaSpecificationExecutor<BranchEntity> {

    boolean existsByBranchCodeAndStatus(String branchCode, String status);

    List<BranchEntity> findAllByNameOrBranchCode(String name, String branchCode);

    Optional<BranchEntity> findByIdAndStatus(UUID id, String status);

    List<BranchEntity> findAllByStatus(String status);

    @Query("""
            SELECT b FROM BranchEntity b 
            WHERE b.name || ' ' || b.branchCode || ' ' || b.email || '' || b.address 
            ILIKE '%' || :keyword || '%'
            """)
    Page<BranchEntity> searchPageBranchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
