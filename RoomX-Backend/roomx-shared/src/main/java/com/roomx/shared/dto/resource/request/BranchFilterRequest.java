package com.roomx.shared.dto.resource.request;


import com.roomx.shared.util.DateTimeUtils;

import java.time.Instant;

public record BranchFilterRequest(
        String searchBy,
        String keyword,
        String status,
        Instant fromDate,
        Instant toDate
) {
}