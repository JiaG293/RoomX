package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.GroupMember;
import com.roomx.domain.model.enums.GroupType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Group {
    private UUID id;
    private String name;

    @Builder.Default
    private String groupType = GroupType.SELF.toString();
    private Branch branch;
    private User userId;
    @Builder.Default
    private List<GroupMember> groupMembers = new ArrayList<>();


}
