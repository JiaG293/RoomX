package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.entity.GroupMember;


import java.util.List;


public interface GroupMemberEntityService {
    GroupMember addMemberToGroup(Group groupDomain, User userDomain);
    List<GroupMember> addListMemberToGroup(Group groupDomain, List<String> members);
}
