package com.roomx.application.service.user;

import com.roomx.application.dto.user.request.RoleCreateRequest;
import com.roomx.application.dto.user.response.RoleResponse;
import com.roomx.application.mapper.PermissionAppMapper;
import com.roomx.application.mapper.RoleAppMapper;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.domain.service.RoleDomainService;
import com.roomx.infrastructure.multitenancy.keycloak.service.KeycloakPermissonService;
import com.roomx.infrastructure.multitenancy.keycloak.service.KeycloakRoleService;
import com.roomx.shared.exception.AppException;
import com.roomx.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleAppService {
    private final RoleDomainService roleDomainService;
    private final PermissionAppMapper permissionAppMapper;
    private final RoleAppMapper roleAppMapper;
    private final KeycloakRoleService keycloakRoleService;
    private final KeycloakPermissonService keycloakPermissonService;
    private final RoleRepository roleRepository;


   /* public RoleResponse createRoleAndAssignPermissons(RolePermissionCreateRequest rolePermissionCreateRequest) {

        var permissionsDomain = rolePermissionCreateRequest.getPermissions()
                .stream()
                .map(permissionAppMapper::toDomain)
                .collect(Collectors.toSet());

        try {
            // Tạo role trên Keycloak trước
            keycloakRoleService.createRole(rolePermissionCreateRequest.getRoleName(), rolePermissionCreateRequest.getDescription());

            if (!permissionsDomain.isEmpty()) {
                permissionsDomain.forEach(permission ->
                        keycloakPermissonService
                                .assignPermissonToRole(
                                        permission.getId(),
                                        rolePermissionCreateRequest.getRoleName()
                                ));
            }

            // Sau đó lưu role vào database
            var savedRole = roleDomainService
                    .createRole(
                            rolePermissionCreateRequest.getRoleName(),
                            rolePermissionCreateRequest.getDescription(),
                            permissionsDomain
                    );

            return roleAppMapper.toReponse(savedRole);
        } catch (Exception e) {
            // Nếu DB rollback, xóa role trên Keycloak
            keycloakRoleService.deleteRole(rolePermissionCreateRequest.getRoleName());
            permissionsDomain.forEach(permission ->
                    keycloakPermissonService.revokePermissionFromRole(
                            permission.getId(),
                            rolePermissionCreateRequest.getRoleName()
                    )
            );
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
        }
    }*/

    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {

        try {
            // Tạo role keycloak
            keycloakRoleService.createRole(roleCreateRequest.getRoleName(), roleCreateRequest.getDescription());


            // Lưu role postgresql
            var savedRole = roleDomainService
                    .createRole(
                            roleCreateRequest.getRoleName(),
                            roleCreateRequest.getDescription(),
                            null
                    );

            return roleAppMapper.toResponse(savedRole);
        } catch (Exception e) {
            // Rollback keycloan nếu có lỗi
            keycloakRoleService.deleteRole(roleCreateRequest.getRoleName());
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
        }
    }

    public RoleResponse assignPermission(String roleName, String permisisonName) {
        try {
            var role = roleDomainService.assignPermission(roleName, permisisonName);

            keycloakPermissonService.assignPermissonToRole(permisisonName, roleName);
            return roleAppMapper.toResponse(role);
        } catch (Exception e) {
            // Rollback keycloak nếu có lỗi
            keycloakPermissonService.revokePermissionFromRole(permisisonName, roleName);
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
        }
    }


}
