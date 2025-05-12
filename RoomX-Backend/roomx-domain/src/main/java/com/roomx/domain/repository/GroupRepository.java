package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;


import java.util.Optional;
import java.util.UUID;

public interface GroupRepository {
    Optional<Group> findById(String id, String status);

    Group save(Group group);

    Optional<Group> findByGroupCodeAndStatus(String groupCode, String status);

    boolean checkGroupExistWithStatusAndGroupCode(String groupCode, String defaultString);

}
