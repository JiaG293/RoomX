package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Branch {
    private UUID id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
}

