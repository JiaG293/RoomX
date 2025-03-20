package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.GroupMemberId;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class GroupMember {
    private GroupMemberId id;
    private User user;
    private Group group;

}
