package com.roomx.shared.dto.resource.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchResponse {
    private String id;
    private String branchCode;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;
    private String placeId;

}
