package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private String avatarImage;
    private String userType;
    private String userCode;
    @Builder.Default
    private boolean enable = true;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();
    @Builder.Default
    private Set<Role> roles = new HashSet<>();






}
