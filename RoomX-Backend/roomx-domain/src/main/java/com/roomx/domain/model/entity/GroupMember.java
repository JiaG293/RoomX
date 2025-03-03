package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.GroupMemberId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupMember {
    private GroupMemberId id;
    private User user;
    private Group group;



}
