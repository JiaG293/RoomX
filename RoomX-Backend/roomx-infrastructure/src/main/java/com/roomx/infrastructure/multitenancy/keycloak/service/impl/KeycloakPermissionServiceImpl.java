package com.roomx.infrastructure.multitenancy.keycloak.service.impl;


import com.roomx.infrastructure.multitenancy.keycloak.service.KeycloakPermissonService;
import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import com.roomx.shared.exception.exception.KeycloakNotFoundException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeycloakPermissionServiceImpl implements KeycloakPermissonService {


    private final Keycloak keycloak;

    @Value("${keycloak.client-default}")
    private String CLIENT_DEFAULT;

    private String getRealm(){
        return TenantContextHolder.getRequiredTenantIdentifier();
    }

    @Override
    public void createPermission(String namePermission, String description) {
        RealmResource realmResource = keycloak.realm(getRealm());
        ClientsResource clientsResource = realmResource.clients();
        ClientResource clientResource = clientsResource.get(CLIENT_DEFAULT);


        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(namePermission.toUpperCase());
        roleRepresentation.setDescription(description);


        try {
            clientResource.roles().create(roleRepresentation);
        } catch (Exception e) {
            throw new KeycloakNotFoundException(ErrorCode.PERMISSION_CREATE_FAILED.getMessageKey());
        }
    }

    @Override
    public void assignPermissonToRole(String permissonName, String roleName) {
        RealmResource realmResource = keycloak.realm(getRealm());

        // Lấy role
        RoleResource realmRoleResource = realmResource.roles().get(roleName);
        RoleRepresentation realmRole = realmRoleResource.toRepresentation();
        if(realmRole == null){
            throw new KeycloakNotFoundException("role id not found");
        }

        // Lấy clientid từ client
        ClientsResource clientsResource = realmResource.clients();
        String clientUuid = clientsResource.findByClientId(CLIENT_DEFAULT).getFirst().getId();
        if (clientUuid.isEmpty()) {
            throw new KeycloakNotFoundException("Client not found");
        }
        ClientResource clientResource = clientsResource.get(clientUuid);

        // Lấy Permission
        RoleResource clientRoleResource = clientResource.roles().get(permissonName);
        RoleRepresentation clientRole = clientRoleResource.toRepresentation();
        if (clientRole == null) {
            throw new KeycloakNotFoundException("Permission not found");
        }


        // Gán Client Role vào Realm Role
        realmRoleResource.addComposites(Collections.singletonList(clientRole));

    }

    public void revokePermissionFromRole(String permissionName, String roleName) {
        RealmResource realmResource = keycloak.realm(getRealm());

        // Lấy Realm Role
        RoleResource realmRoleResource = realmResource.roles().get(roleName);
        RoleRepresentation realmRole = realmRoleResource.toRepresentation();
        if (realmRole == null) {
            throw new NotFoundException("Role not found");
        }

        // Lấy Client Role
        ClientsResource clientsResource = realmResource.clients();
        ClientResource clientResource = clientsResource.get(CLIENT_DEFAULT);
        RoleRepresentation clientRole = clientResource.roles().get(permissionName).toRepresentation();
        if (clientRole == null) {
            throw new NotFoundException("Permission not found");
        }

        // Kiểm tra xem Role có chứa Permission không
        Set<RoleRepresentation> compositeRoles = realmRoleResource.getRoleComposites();
        if (compositeRoles.stream().noneMatch(role -> role.getName().equalsIgnoreCase(permissionName))) {
            throw new IllegalArgumentException("Role does not have this permission");
        }

        // Xóa permission khỏi role
        realmRoleResource.deleteComposites(Collections.singletonList(clientRole));
    }
}
