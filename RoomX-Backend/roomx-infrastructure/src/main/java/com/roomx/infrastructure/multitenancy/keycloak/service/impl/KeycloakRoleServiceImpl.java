package com.roomx.infrastructure.multitenancy.keycloak.service.impl;

import com.roomx.infrastructure.multitenancy.keycloak.service.KeycloakRoleService;
import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakRoleServiceImpl implements KeycloakRoleService {
    private final Keycloak keycloak;

    private String getRealm() {
        return TenantContextHolder.getRequiredTenantIdentifier();
    }

    public List<String> findAll(){
        List<RoleRepresentation> roles = keycloak.realm(getRealm()).roles().list();
        return roles.stream().map(RoleRepresentation::getName).toList();
    }


    @Override
    public void createRole(String nameRole, String description) {
        RealmResource realmResource = keycloak.realm(getRealm());
        RolesResource rolesResource = realmResource.roles();

        // Tạo RoleRepresentation
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(nameRole.toUpperCase());
        roleRepresentation.setDescription(description);

        // Tạo Realm Role
        rolesResource.create(roleRepresentation);

    }

    @Override
    public void deleteRole(String nameRole) {
        RealmResource realmResource = keycloak.realm(getRealm());
        RolesResource rolesResource = realmResource.roles();
        rolesResource.deleteRole(nameRole.toUpperCase());
    }
}
