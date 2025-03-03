package com.roomx.infrastructure.multitenancy.keycloak.service;


import org.keycloak.representations.idm.RoleRepresentation;

public interface KeycloakRoleService {
    void createRole(String nameRole, String description);
    void deleteRole(String nameRole);


//    RoleRepresentation updateRole(RoleRepresentation roleRepresentation);
}
