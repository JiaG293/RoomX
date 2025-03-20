package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.domain.repository.GroupMemberRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.GroupMemberEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaGroupMemberRepository;
import com.roomx.shared.enums.DeleteStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GroupMemberEntityRepository implements GroupMemberRepository {
    private final JpaGroupMemberRepository jpaGroupMemberRepository;
    private final GroupMemberEntityMapper groupMemberEntityMapper;

    @Override
    public Optional<GroupMember> findById(GroupMemberId groupMemberId) {
        return jpaGroupMemberRepository
                .findByIdAndGroupStatus(groupMemberId, DeleteStatusType.getDefaultString())
                .map(groupMemberEntityMapper::toDomain);
    }

    @Override
    public Optional<GroupMember> findByGroupId(String groupId) {
        return jpaGroupMemberRepository
                .findByGroupId(UUID.fromString(groupId))
                .map(groupMemberEntityMapper::toDomain);
    }

    @Override
    public Optional<GroupMember> findByUserId(String userId) {
        return jpaGroupMemberRepository
                .findByUserId(UUID.fromString(userId))
                .map(groupMemberEntityMapper::toDomain);
    }

    @Override
    public GroupMember save(GroupMember groupMember) {
        var groupMemberEntity = groupMemberEntityMapper.toEntity(groupMember);
        var savedGroupMemberEntity = jpaGroupMemberRepository.save(groupMemberEntity);
        return groupMemberEntityMapper.toDomain(savedGroupMemberEntity);
    }

    @Override
    public List<GroupMember> saveAll(List<GroupMember> listGroupMembers) {
        var groupMemberEntityList = listGroupMembers.stream()
                .map(groupMemberEntityMapper::toEntity)
                .toList();

        var savedGroupMemberEntityList = jpaGroupMemberRepository
                .saveAll(groupMemberEntityList);

        return savedGroupMemberEntityList
                .stream().map(groupMemberEntityMapper::toDomain)
                .toList();
    }
}
