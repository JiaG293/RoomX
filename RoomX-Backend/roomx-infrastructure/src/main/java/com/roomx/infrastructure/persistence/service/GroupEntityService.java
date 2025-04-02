package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;


public interface GroupEntityService {
    Group createGroup(GroupCreateAdminRequest request, String userId, String role);
}
