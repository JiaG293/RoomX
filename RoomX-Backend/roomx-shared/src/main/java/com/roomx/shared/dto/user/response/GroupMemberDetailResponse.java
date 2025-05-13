package com.roomx.shared.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberDetailResponse {
    private String id;
    private String email;
    private String userCode;
    private String lastName;
    private String firstName;
}
