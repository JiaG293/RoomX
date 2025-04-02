package com.roomx.infrastructure.keycloak.service.impl;


import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl {
    private final Keycloak keycloak;

    private String getRealm() {
        return TenantContextHolder.getRequiredTenantIdentifier();
    }

    public void save(UserRepresentation userRepresentation) {
        Response response = keycloak.realm(getRealm()).users().create(userRepresentation);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
        response.close();
    }

    public Optional<UserRepresentation> findUserById(String id) {
        try {
            return Optional.ofNullable(keycloak.realm(getRealm()).users().get(id).toRepresentation());
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    public Optional<UserRepresentation> findUserByEmail(String email) {
        List<UserRepresentation> users = keycloak.realm(getRealm())
                .users()
                .search(null, null, null, email, 0, 1);
        return users.stream().findFirst();
    }

    public Optional<UserRepresentation> findByUserCode(String userCode) {
        List<UserRepresentation> users = keycloak.realm(getRealm())
                .users()
                .search(userCode, null, null, null, 0, 1);
        return users.stream().findFirst();
    }

    public void delete(String id) {
        keycloak.realm(getRealm()).users().get(id).remove();
    }

    public List<UserRepresentation> findAll() {
        return keycloak.realm(getRealm()).users().list();
    }
}


