package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.GroupMemberEntity;
import com.roomx.infrastructure.persistence.model.ids.GroupMemberEntityId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaGroupMemberRepository extends JpaRepository<GroupMemberEntity, GroupMemberEntityId>, JpaSpecificationExecutor<GroupMemberEntity> {

    Optional<GroupMemberEntity> findByIdAndGroupStatus(GroupMemberEntityId groupMemberId, String status);

    Optional<GroupMemberEntity> findByGroupId(UUID groupId);

    Optional<GroupMemberEntity> findByUserId(UUID userId);

    List<GroupMemberEntity> findAllByGroupId(UUID groupId);
}
