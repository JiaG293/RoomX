/*
package com.roomx.infrastructure.multitenancy.keycloak.repository;

import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class KeycloakUserRepresentationRepository {

    Keycloak keycloak;
    com.roomx.infrastructure.multitenancy.keycloak.mapper.UserRepresentationMapper userRepresentationMapper;

    @Override
    public Optional<User> findById(String realm, String id) {
        try {
            var user = keycloak.realm(realm).users().search(id, 0, 1);
            if (user.isEmpty()) {
                log.warn("User not found: {}", id);
                return Optional.empty();
            }
            return Optional.of(userRepresentationMapper.toUserDomain(user.getFirst()));
        } catch (NotFoundException e) {
            log.error("User not found in realm {}: {}", realm, id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDomain> findByEmail(String realm, String email) {
        try {
            var users = keycloak.realm(realm).users().search(null, null, null, email, 0, 1, false, false);
            if (users.isEmpty()) {
                log.warn("Email not found: {}", email);
                return Optional.empty();
            }
            return Optional.of(userRepresentationMapper.toUserDomain(users.getFirst()));
        } catch (NotFoundException e) {
            log.error("Email not found in realm {}: {}", realm, email);
            return Optional.empty();
        }
    }

    @Override
    public void save(String realm, UserDomain userDomain) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDomain.getMaNhanVien());
        user.setEmail(userDomain.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userDomain.getPassword());

        user.setCredentials(Collections.singletonList(passwordCred));

        keycloak.realm(realm).users().create(user);
    }

    @Override
    public void updateUser(String realm, String id, UserDomain userDomain) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();
        user.setEmail(userDomain.getEmail());
        userResource.update(user);
    }

    @Override
    public void deleteById(String realm, String id) {
        keycloak.realm(realm).users().delete(id);
    }
}
*/
