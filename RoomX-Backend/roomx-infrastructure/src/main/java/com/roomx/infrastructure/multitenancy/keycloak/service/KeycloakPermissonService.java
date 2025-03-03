package com.roomx.infrastructure.multitenancy.keycloak.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

public interface KeycloakPermissonService {

    void createPermission(String namePermission, String description);

    void assignPermissonToRole(String permissonName, String roleName);

    void revokePermissionFromRole(String permissonName, String roleName);


}
