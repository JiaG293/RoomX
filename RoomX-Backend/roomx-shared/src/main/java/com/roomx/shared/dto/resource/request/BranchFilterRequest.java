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
    public BranchFilterRequest(String searchBy, String keyword, String status, String fromDate, String toDate) {
        this(
                searchBy,
                keyword,
                status,
                DateTimeUtils.parseToInstant(fromDate, false),
                DateTimeUtils.parseToInstant(toDate, true)
        );
    }
}