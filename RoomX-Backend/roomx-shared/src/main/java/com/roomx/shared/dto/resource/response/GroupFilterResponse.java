package com.roomx.shared.dto.resource.response;

import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupFilterResponse {
    private UUID id;
    private String name;
    private String groupType;
    private String groupCode;
    private PlaceResponse branch;
    private UserResponse owner;
    private Integer quantityMember;
    private String status;
}
