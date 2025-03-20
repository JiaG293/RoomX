package com.roomx.application.service.user;

import com.roomx.application.mapper.GroupAppMapper;
import com.roomx.domain.repository.GroupRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.GroupEntityService;
import com.roomx.infrastructure.multitenancy.security.oauth.SecurityUtil;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupAppService {
    private final GroupRepository groupRepository;
    private final GroupAppMapper groupAppMapper;
    private final SecurityUtil securityUtil;
    private final GroupEntityService groupEntityService;

    @Transactional
    public GroupResponse createGroupForAdmin(GroupCreateAdminRequest request) {
        var userId = securityUtil.getCurrentUserId();
        var groupDomain = groupEntityService.createGroup(request, userId, "ADMIN");

        return groupAppMapper.toResponse(groupDomain);
    }

    @Transactional
    public GroupResponse createGroupForUser(GroupCreateAdminRequest request) {
        var userId = securityUtil.getCurrentUserId();
        var groupDomain = groupEntityService.createGroup(request, userId, "USER");

        return groupAppMapper.toResponse(groupDomain);
    }

    public GroupResponse addMemberGroupForAdmin(String groupId, String memberId) {
        return null;
    }
}
