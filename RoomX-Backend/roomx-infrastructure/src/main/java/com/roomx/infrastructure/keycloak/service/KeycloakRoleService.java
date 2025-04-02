package com.roomx.infrastructure.keycloak.service;


public interface KeycloakRoleService {
    void createRole(String nameRole, String description);
    void deleteRole(String nameRole);


//    RoleRepresentation updateRole(RoleRepresentation roleRepresentation);
}
