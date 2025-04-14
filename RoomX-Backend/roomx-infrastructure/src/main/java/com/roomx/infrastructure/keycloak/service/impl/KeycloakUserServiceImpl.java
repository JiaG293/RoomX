package com.roomx.infrastructure.keycloak.service.impl;


import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void changeStatusUser(String id, boolean status) {
        keycloak.realm(getRealm())
                .users()
                .get(id)
                .update(new UserRepresentation() {{
                    setEnabled(status);
                }});
    }


    public List<UserRepresentation> findAll() {
        return keycloak.realm(getRealm()).users().list();
    }

    public void addRole(String id, String role) {
        UserRepresentation user = keycloak.realm(getRealm())
                .users()
                .get(id)
                .toRepresentation();

        Map<String, List<String>> attributes = user.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        List<String> roles = attributes.get("roles");
        if (roles == null) {
            roles = new ArrayList<>();
        }

        if (!roles.contains(role)) {
            roles.add(role);
        }

        attributes.put("roles", roles);
        user.setAttributes(attributes);

        keycloak.realm(getRealm())
                .users()
                .get(id)
                .update(user);
    }

    public void removeRole(String id, String role) {
        UserRepresentation user = keycloak.realm(getRealm())
                .users()
                .get(id)
                .toRepresentation();

        Map<String, List<String>> attributes = user.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        List<String> roles = attributes.get("roles");
        if (roles != null) {
            roles.remove(role);

            attributes.put("roles", roles);
            user.setAttributes(attributes);

            keycloak.realm(getRealm())
                    .users()
                    .get(id)
                    .update(user);
        }
    }

    public void updateRoles(String id, List<String> newRoles) {
        UserRepresentation user = keycloak.realm(getRealm())
                .users()
                .get(id)
                .toRepresentation();

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("roles", newRoles);
        user.setAttributes(attributes);

        keycloak.realm(getRealm())
                .users()
                .get(id)
                .update(user);
    }

}


