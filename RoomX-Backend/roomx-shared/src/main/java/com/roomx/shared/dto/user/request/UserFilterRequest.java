package com.roomx.shared.dto.user.request;

import java.util.List;

public record UserFilterRequest(
        String keyword,
        String searchBy,

        String userType,
        String branchId,
        String groupId,
        Boolean enabled,
        List<String> roles
) {
}
