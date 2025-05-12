package com.roomx.shared.dto.resource.request;

import java.math.BigDecimal;

public record GroupFilterRequest(
        String keyword,
        String searchBy,

        String groupType,
        String branchId,
        String createByUser,
        String status,

        Boolean isAdmin

) {
}
