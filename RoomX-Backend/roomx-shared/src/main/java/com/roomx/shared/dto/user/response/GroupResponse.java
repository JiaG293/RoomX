package com.roomx.shared.dto.user.response;

import com.roomx.shared.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private UUID id;
    private String name;
    private String groupType;
    private String groupCode;
    private String branchId;
    private String userId;
    private List<GroupMemberResponse> groupMembers;
}
