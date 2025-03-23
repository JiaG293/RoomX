package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.GroupMemberEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.GroupMemberEntityId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaGroupMemberRepository extends JpaRepository<GroupMemberEntity, GroupMemberEntityId>, JpaSpecificationExecutor<GroupMemberEntity> {

    Optional<GroupMemberEntity> findByIdAndGroupStatus(GroupMemberEntityId groupMemberId, String status);

    Optional<GroupMemberEntity> findByGroupId(UUID groupId);

    Optional<GroupMemberEntity> findByUserId(UUID userId);

    List<GroupMemberEntity> findAllByGroupId(UUID groupId);
}
