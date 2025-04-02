package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.domain.repository.BranchRepository;
import com.roomx.domain.repository.GroupMemberRepository;
import com.roomx.domain.repository.GroupRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.persistence.mapper.GroupEntityMapper;
import com.roomx.infrastructure.persistence.service.GroupEntityService;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.GroupType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class GroupEntityServiceImpl implements GroupEntityService {
    private final GroupRepository groupRepository;
    private final GroupEntityMapper groupEntityMapper;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final GroupMemberRepository groupMemberRepository;


    @Override
    @Transactional
    public Group createGroup(GroupCreateAdminRequest request, String userId, String role) {
        var checkGroupExist = groupRepository.findByGroupCodeAndStatus(request.getGroupCode(), DeleteStatusType.getDefaultString());

        if (checkGroupExist.isPresent()) {
            throw new AppException(ErrorCode.GROUP_CONFLICT, checkGroupExist.get().getGroupCode());
        }

        var userDomain = userRepository.findById(UUID.fromString(userId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Branch branchDomain = null;
        if (
                !request.getBranchId().isEmpty() &&
                        (request.getGroupType().equals(GroupType.DEPARTMENT.toString()) ||
                                request.getGroupType().equals(GroupType.SELF.toString()))
        ) {
            branchDomain = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));
        }


        var groupDomain = Group.builder()
                .groupCode(request.getGroupCode())
                .name(request.getName())
                .branch(branchDomain)
                .groupType(request.getGroupType())
                .user(userDomain)
                .status(DeleteStatusType.getDefaultString())
                .build();

        var savedGroupDomain = groupRepository.save(groupDomain);

        List<GroupMember> groupMembers = new ArrayList<>();
        var failedAddMembers = new ArrayList<String>();

        if (!request.getGroupMembers().isEmpty()) {
            Group finalSavedGroupDomain = savedGroupDomain;
            groupMembers = request.getGroupMembers().stream()
                    .map(memberId -> {
                        var userFind = userRepository.findById(UUID.fromString(memberId), true);

                        if (userFind.isEmpty()) {
                            failedAddMembers.add(memberId);
                            return null;
                        }

                        return GroupMember.builder()
                                .id(new GroupMemberId(userFind.get().getId(), finalSavedGroupDomain.getId()))
                                .group(finalSavedGroupDomain)
                                .user(userFind.get())
                                .build();
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }

        var savedListGroupMembers = groupMemberRepository.saveAll(groupMembers);
        savedGroupDomain.setGroupMembers(savedListGroupMembers);
        return savedGroupDomain;

    }
}
