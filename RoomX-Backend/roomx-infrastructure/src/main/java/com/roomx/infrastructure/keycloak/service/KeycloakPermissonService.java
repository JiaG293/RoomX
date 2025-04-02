package com.roomx.infrastructure.keycloak.service;

public interface KeycloakPermissonService {

    void createPermission(String namePermission, String description);

    void assignPermissonToRole(String permissonName, String roleName);

    void revokePermissionFromRole(String permissonName, String roleName);


}
