package com.roomx.domain.service;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.domain.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleDomainService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleDomainService(RoleRepository roleRepository,
                             UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public Role createRole(String id, String description) {
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

    public Role deleteRole(String id) {
        var role = roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Role not found")
        );


        roleRepository.save(role);

        return role;
    }







}
