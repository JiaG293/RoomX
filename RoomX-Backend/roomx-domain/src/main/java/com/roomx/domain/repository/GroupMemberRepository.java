package com.roomx.domain.repository;


import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository {
    Optional<GroupMember> findById(GroupMemberId groupMemberId);
    Optional<GroupMember> findByGroupId(String groupId);
    Optional<GroupMember> findByUserId(String userId);
    GroupMember save(GroupMember groupMember);
    List<GroupMember> saveAll(List<GroupMember> listGroupMembers);

    List<GroupMember> findAllByGroupId(String groupId);
}
