package com.roomx.shared.dto.user.response;


import com.roomx.shared.dto.resource.response.PlaceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDetailResponse {
    private UUID id;
    private String name;
    private String groupType;
    private String groupCode;
    private PlaceResponse branch;
    private UserResponse owner;
    private Integer quantityMember;
    private List<GroupMemberDetailResponse> members;
    private String status;
}