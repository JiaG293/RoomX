package com.roomx.application.dto.resource.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchQueryFilterRequest {
    private String branchCode = null;
    private String name = null;
    private String phoneNumber = null;
    private String email = null;
    private String address = null;
    private boolean typeCompare = false; // false = and || true = or
}
