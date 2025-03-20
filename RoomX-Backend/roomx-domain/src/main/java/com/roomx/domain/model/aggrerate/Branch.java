package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.time.Instant;
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

    private String status;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();
}

