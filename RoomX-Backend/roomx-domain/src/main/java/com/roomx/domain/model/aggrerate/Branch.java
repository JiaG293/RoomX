package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Branch {
    private UUID id;
    private String branchCode;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
}

