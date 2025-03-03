package com.roomx.domain.service;

import com.roomx.domain.model.entity.Permission;
import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.repository.PermissionRepository;
import com.roomx.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleDomainService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleDomainService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role createRole(String id, String description, Set<Permission> permissions) {
        String roleName = id.toUpperCase();
        roleRepository.findById(roleName).ifPresent(role -> {
            throw new IllegalArgumentException("Role already exists");
        });
        var role = Role.builder()
                .id(roleName)
                .description(description)
                .build();
        roleRepository.save(role);

        return role;
    }

    public Role assignPermission(String roleName, String permissionName) {
        var roleDomain = roleRepository.findById(roleName)
                        .orElseThrow(()-> new RuntimeException("Not found role domain"));
        var permissionDomain = permissionRepository.findById(permissionName)
                .orElseThrow(() -> new RuntimeException("Not found permission domain"));

        roleDomain.addPermission(permissionDomain);

        roleRepository.save(roleDomain);

        return roleDomain;
    }







}
