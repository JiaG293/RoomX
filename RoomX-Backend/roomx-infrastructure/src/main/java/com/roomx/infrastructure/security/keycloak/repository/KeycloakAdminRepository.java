/*
package com.roomx.infrastructure.persistence.security.keycloak.repository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeycloakAdminRepository {


    private final Keycloak keycloak;
    private final String realm = "RoomX";

    @Autowired
    public KeycloakAdminRepository(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createUser(UserRepresentation user) {
        keycloak.realm(realm).users().create(user);
    }

    public void assignRoleToUser(String userId, RoleRepresentation role) {
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));
    }

    public List<UserRepresentation> getUsers() {
        return keycloak.realm(realm).users().list();
    }

    public void deleteUser(String userId) {
        keycloak.realm(realm).users().get(userId).remove();
    }
}


*/
