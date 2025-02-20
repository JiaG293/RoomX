package com.roomx.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {
    private List<UserResponse> users;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
}
