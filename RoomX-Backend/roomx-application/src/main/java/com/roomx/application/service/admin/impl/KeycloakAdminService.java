/*
package com.roomx.application.service.admin.impl;

import com.roomx.infrastructure.persistence.security.keycloak.repository.KeycloakAdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KeycloakAdminService {


    KeycloakAdminRepository keycloakAdminRepository;


    public String createUser(String username, String email, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);

        keycloakClient.createUser(user);
        return "User created successfully!";
    }

    public String assignRoleToUser(String userId, String roleName) {
        RoleRepresentation role = keycloakClient.keycloak()
                .realm("myrealm")
                .roles()
                .get(roleName)
                .toRepresentation();
        keycloakClient.assignRoleToUser(userId, role);
        return "Role assigned successfully!";
    }

    public List<UserRepresentation> getUsers() {
        return keycloakClient.getUsers();
    }

    public String deleteUser(String userId) {
        keycloakClient.deleteUser(userId);
        return "User deleted successfully!";
    }
}*/
