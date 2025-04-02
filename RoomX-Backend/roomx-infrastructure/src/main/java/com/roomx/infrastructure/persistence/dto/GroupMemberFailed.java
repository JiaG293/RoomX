package com.roomx.infrastructure.persistence.dto;

import com.roomx.domain.model.entity.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberFailed {
    private List<String> failedAddMembers;
    private List<GroupMember> groupMembers;
}
