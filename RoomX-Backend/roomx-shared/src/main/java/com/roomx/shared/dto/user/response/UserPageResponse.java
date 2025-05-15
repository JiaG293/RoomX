package com.roomx.shared.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {
    private String id;
    private String userCode;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarImage;
    private String phoneNumber;
    private String userType;
    private Boolean enable;

    private List<String> roles;
    private UUID branchId;
    private String branchName;
    private UUID groupId;
    private String groupName;
    private String groupType;


}
