package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.domain.repository.GroupMemberRepository;
import com.roomx.domain.repository.GroupRepository;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.persistence.dto.GroupFilter;
import com.roomx.infrastructure.persistence.mapper.GroupEntityMapper;
import com.roomx.infrastructure.persistence.model.projection.GroupProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaGroupEntityRepository;
import com.roomx.infrastructure.persistence.service.GroupEntityService;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.request.GroupCreateRequest;
import com.roomx.shared.dto.user.response.GroupDetailResponse;
import com.roomx.shared.dto.user.response.GroupMemberDetailResponse;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.GroupType;
import com.roomx.shared.enums.PlaceType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class GroupEntityServiceImpl implements GroupEntityService {
    private final GroupRepository groupRepository;
    private final GroupEntityMapper groupEntityMapper;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final JpaGroupEntityRepository jpaGroupEntityRepository;
    private final PlaceRepository placeRepository;
    private final SecurityUtil securityUtil;
    private final PlaceEntityServiceImpl placeEntityService;


    @Override
    @Transactional
    public Group createGroup(GroupCreateRequest request, String userId, String role) {
        var checkGroupExist = groupRepository.findByGroupCodeAndStatus(request.getGroupCode(), DeleteStatusType.getDefaultString());

        if (checkGroupExist.isPresent()) {
            throw new AppException(ErrorCode.GROUP_CONFLICT, checkGroupExist.get().getGroupCode());
        }

        var userDomain = userRepository.findById(UUID.fromString(userId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Place branchDomain = null;
        if (
                !request.getBranchId().isEmpty() &&
                        (request.getGroupType().equals(GroupType.DEPARTMENT.toString()) ||
                                request.getGroupType().equals(GroupType.SELF.toString()))
        ) {
            branchDomain = placeRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));
        } else if (role == "USER") {
            branchDomain = placeEntityService.findBranchByMemberIdAndGrouPType(
                            userDomain.getId(),
                            GroupType.DEPARTMENT.toString())
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
                    .map(email -> {
                        var userFind = userRepository.findByEmail(email, true);

                        if (userFind.isEmpty()) {
                            failedAddMembers.add(email);
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

    @Override
    public Page<GroupProjection> filterSearchGroup(GroupFilter filter, Pageable pageable) {
        return jpaGroupEntityRepository
                .findGroupWithFilters(
                        filter.getKeyword(),
                        filter.getSearchBy(),
                        filter.getStatus(),
                        filter.getGroupType(),
                        filter.getBranchId(),
                        filter.getUserId(),
                        filter.getIsAdmin(),
                        filter.getViewAsUser(),
                        pageable
                );
    }

    @Override
    public Optional<GroupDetailResponse> getDetailGroup(String groupId, String status) {
        return jpaGroupEntityRepository.findByIdDetail(UUID.fromString(groupId), status)
                .map(group -> GroupDetailResponse.builder()
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
                        .members(group.getMembers())
                        .status(group.getStatus())
                        .build());
    }
}
