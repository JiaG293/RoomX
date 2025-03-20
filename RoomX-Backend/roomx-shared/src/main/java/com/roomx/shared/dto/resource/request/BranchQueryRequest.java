package com.roomx.shared.dto.resource.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchQueryRequest {
    private String branchCode = null;
    private String name = null;
    private String phoneNumber = null;
    private String email = null;
    private String address = null;
    private boolean typeCompare = false; // false = and || true = or
}
