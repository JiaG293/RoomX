package com.roomx.application.service.user;

import com.roomx.application.mapper.GroupAppMapper;
import com.roomx.application.mapper.GroupMemberAppMapper;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.domain.repository.GroupMemberRepository;
import com.roomx.domain.repository.GroupRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.persistence.dto.GroupFilter;
import com.roomx.infrastructure.persistence.service.GroupEntityService;
import com.roomx.infrastructure.security.oauth.RoleEvaluator;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import com.roomx.shared.dto.resource.request.GroupFilterRequest;
import com.roomx.shared.dto.resource.response.GroupFilterResponse;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.request.GroupCreateRequest;
import com.roomx.shared.dto.user.request.GroupCreateUserRequest;
import com.roomx.shared.dto.user.response.GroupDetailResponse;
import com.roomx.shared.dto.user.response.GroupMemberDetailResponse;
import com.roomx.shared.dto.user.response.GroupResponse;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupAppService {
    private final GroupRepository groupRepository;
    private final GroupAppMapper groupAppMapper;
    private final SecurityUtil securityUtil;
    private final GroupEntityService groupEntityService;
    private final GroupMemberAppMapper groupMemberAppMapper;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final RoleEvaluator roleEvaluator;

    @Transactional
    @PreAuthorize("@roleEvaluator.hasAnyRoleType('approve')")
    public GroupResponse createGroupForAdmin(GroupCreateAdminRequest request) {
        var userId = securityUtil.getCurrentUserId();
        var requestGroup = groupAppMapper.adminToGroupRequest(request);
        var groupDomain = groupEntityService.createGroup(requestGroup, userId, "ADMIN");

        return groupAppMapper.toResponse(groupDomain);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public GroupResponse createGroupForUser(GroupCreateUserRequest request) {
        var userId = securityUtil.getCurrentUserId();
        var requestGroup = groupAppMapper.userToGroupRequest(request);
        var groupDomain = groupEntityService.createGroup(requestGroup, userId, "USER");

        return groupAppMapper.toResponse(groupDomain);
    }

    @Transactional
    public void deleteMemberFromGroup(String groupId, String memberId) {
        var groupDomain = groupRepository.findById(groupId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_FOUND));

        groupMemberRepository
                .findById(new GroupMemberId(UUID.fromString(memberId), groupDomain.getId()))
                .orElseThrow(() -> new AppException(ErrorCode.GROUPMEMBER_NOTFOUND, memberId));

        groupMemberRepository.deleteById(groupId, memberId);
    }

    @Transactional
    public Map<String, List<String>> deleteListMemberFromGroup(String groupId, List<String> members) {
        var groupDomain = groupRepository.findById(groupId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_FOUND));
        var memberExist = new ArrayList<GroupMemberId>();
        var memberNotExist = members.stream().map(member -> {
            var groupMemberId = new GroupMemberId(UUID.fromString(member), groupDomain.getId());
            var memberFind = groupMemberRepository
                    .findById(groupMemberId);
            if (memberFind.isPresent()) {
                memberExist.add(groupMemberId);
            }
            return member;
        }).toList();
        groupMemberRepository.deleteAllById(memberExist);

        return Map.of("memberNotExist", memberNotExist);
    }


    public boolean checkMemberIsExistedGroup(String groupId, String memberId) {
        var groupMemberDomain = groupMemberRepository
                .findById(new GroupMemberId(UUID.fromString(groupId), UUID.fromString(memberId)));
        return groupMemberDomain.isPresent();
    }

    @Transactional
    public GroupResponse addMemberGroupForAdmin(String groupId, String memberId) {
        var groupDomain = groupRepository.findById(groupId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_FOUND));


        var userDomain = userRepository.findById(UUID.fromString(memberId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var groupMember = GroupMember.builder()
                .user(userDomain)
                .group(groupDomain)
                .id(new GroupMemberId(userDomain.getId(), groupDomain.getId()))
                .build();

        groupMemberRepository.save(groupMember);

        var groupMemberDomainList = groupMemberRepository.findAllByGroupId(groupId);
        groupDomain.setGroupMembers(groupMemberDomainList);

        return groupAppMapper.toResponse(groupDomain);
    }

    @Transactional
    public GroupResponse addMemberListGroupForAdmin(String groupId, List<String> memberList) {
        var groupDomain = groupRepository.findById(groupId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_FOUND));

        var failedAddMembers = new ArrayList<String>();

        var groupMembers = memberList.stream()
                .map(memberId -> {
                    var userFind = userRepository.findById(UUID.fromString(memberId), true);
                    if (userFind.isEmpty()) {
                        failedAddMembers.add(memberId);
                        return null;
                    }
                    return GroupMember.builder()
                            .id(new GroupMemberId(userFind.get().getId(), groupDomain.getId()))
                            .group(groupDomain)
                            .user(userFind.get())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        groupMemberRepository.saveAll(groupMembers);

        var listMemberGroup = groupMemberRepository.findAllByGroupId(groupId);

        groupDomain.setGroupMembers(listMemberGroup);

        return groupAppMapper.toResponse(groupDomain);
    }

    public Page<GroupFilterResponse> filterSearchGroup(
            GroupFilterRequest request,
            Integer page,
            Integer size,
            String sortBy,
            String direction) {

        if (size == -1) {
            size = Integer.MAX_VALUE;
        }
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        String userId = securityUtil.getCurrentUserId();


        GroupFilter groupFilter = null;

        if (!roleEvaluator.hasAnyRoleType("approve")) {
            groupFilter = GroupFilter.builder()
                    .keyword(request.keyword())
                    .searchBy(request.searchBy())
                    .branchId(request.branchId())
                    .userId(userId)
                    .groupType(request.groupType())
                    .status(request.status())
                    .build();
        } else {
            if (request.isAdmin() == null || request.isAdmin()) {
                groupFilter = GroupFilter.builder()
                        .keyword(request.keyword())
                        .searchBy(request.searchBy())
                        .branchId(request.branchId())
                        .userId(userId)
                        .viewAsUser(false)
                        .isAdmin(true)
                        .groupType(request.groupType())
                        .status(request.status())
                        .build();
            } else {
                groupFilter = GroupFilter.builder()
                        .keyword(request.keyword())
                        .searchBy(request.searchBy())
                        .branchId(request.branchId())
                        .userId(userId)
                        .viewAsUser(true)
                        .isAdmin(true)
                        .groupType(request.groupType())
                        .status(request.status())
                        .build();
            }
        }

        return groupEntityService
                .filterSearchGroup(groupFilter, pageable)
                .map(group -> GroupFilterResponse.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .groupType(group.getGroupType())
                        .groupCode(group.getGroupCode())
                        .branch(PlaceResponse.builder()
                                .id(group.getBranchId().toString())
                                .name(group.getBranchName())
                                .code(group.getBranchCode())
                                .placeType(PlaceType.BRANCH.toString())
                                .build())
                        .owner(UserResponse.builder()
                                .id(group.getCreatedBy().toString())
                                .userCode(group.getOwnerUserCode())
                                .email(group.getOwnerEmail())
                                .firstName(group.getOwnerFirstName())
                                .lastName(group.getOwnerLastName())
                                .build())
                        .quantityMember(group.getQuantityMember())
                        .status(group.getStatus())
                        .build());
    }

    public GroupDetailResponse getDetailGroup(String groupId) {
        return groupEntityService.getDetailGroup(groupId, DeleteStatusType.ACTIVE.toString())
                .orElseThrow(() -> new AppException(ErrorCode.GROUP_NOT_FOUND));
    }
}
