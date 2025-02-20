package com.roomx.infrastructure.multitenancy.keycloak.repository;

import com.roomx.infrastructure.multitenancy.keycloak.repository.UserRepresentationRepository;
import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KeycloakAdminRepository implements UserRepresentationRepository {
    private final Keycloak keycloak;
    private final String CLIENT_ID_DEFAULT = "roomx-fe";

    @Autowired
    public KeycloakAdminRepository(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    private String getRealm() {
        return TenantContextHolder.getRequiredTenantIdentifier();
    }

    @Override
    public void save(UserRepresentation userRepresentation) {
        Response response = keycloak.realm(getRealm()).users().create(userRepresentation);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
        response.close();
    }

    @Override
    public Optional<UserRepresentation> findUserById(String id) {
        try {
            return Optional.ofNullable(keycloak.realm(getRealm()).users().get(id).toRepresentation());
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserRepresentation> findUserByEmail(String email) {
        List<UserRepresentation> users = keycloak.realm(getRealm())
                .users()
                .search(null, null, null, email, 0, 1);
        return users.stream().findFirst();
    }

    @Override
    public Optional<UserRepresentation> findUserByEmployeeId(String employeeId) {
        List<UserRepresentation> users = keycloak.realm(getRealm())
                .users()
                .search(employeeId, null, null, null, 0, 1);
        return users.stream().findFirst();
    }
    @Override
    public void delete(String id) {
        keycloak.realm(getRealm()).users().get(id).remove();
    }

    @Override
    public List<UserRepresentation> findAll() {
        return keycloak.realm(getRealm()).users().list();
    }
}


