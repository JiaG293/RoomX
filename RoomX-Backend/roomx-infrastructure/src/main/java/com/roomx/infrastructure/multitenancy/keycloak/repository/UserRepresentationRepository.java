package com.roomx.infrastructure.multitenancy.keycloak.repository;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface UserRepresentationRepository {
    void save(UserRepresentation userRepresentation);
    Optional<UserRepresentation> findUserById(String id);
    Optional<UserRepresentation> findUserByEmail(String email);
    Optional<UserRepresentation> findUserByEmployeeId(String employeeId);
    void delete(String id);
    List<UserRepresentation> findAll();
}
