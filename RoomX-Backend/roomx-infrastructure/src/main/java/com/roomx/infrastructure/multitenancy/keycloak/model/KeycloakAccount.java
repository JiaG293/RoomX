package com.roomx.infrastructure.multitenancy.keycloak.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakAccount {
    private String id;
    private String username;
    private String email;
    private String enabled;
    private String emailVerified;
    private Set<KeycloakRole> roles = new HashSet<>();

}
