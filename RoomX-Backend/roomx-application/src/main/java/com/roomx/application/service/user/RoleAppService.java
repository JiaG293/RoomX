package com.roomx.application.service.user;

import com.roomx.shared.dto.user.request.RoleCreateRequest;
import com.roomx.shared.dto.user.response.RoleResponse;
import com.roomx.shared.dto.user.response.UserRoleResponse;
import com.roomx.application.mapper.RoleAppMapper;
import com.roomx.application.mapper.UserAppMapper;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.domain.repository.UserRoleRepository;
import com.roomx.domain.service.RoleDomainService;
import com.roomx.infrastructure.multitenancy.keycloak.service.KeycloakRoleService;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleAppService {
    private final RoleDomainService roleDomainService;
    private final UserAppMapper userAppMapper;
    private final RoleAppMapper roleAppMapper;
    private final KeycloakRoleService keycloakRoleService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public List<RoleResponse> findAllRole(){
        return roleRepository.findAll().stream().map(roleAppMapper::toResponse).toList();
    }

    public List<RoleResponse> findRolesNotAssignedToUser(String userId){
        return userRoleRepository.findRolesByUserId(UUID.fromString(userId)).stream().map(roleAppMapper::toResponse).collect(Collectors.toList());
    };


    @Transactional
    @PreAuthorize("hasRole('OWNER')")
    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {

        try {
            // Tạo role keycloak
            keycloakRoleService.createRole(roleCreateRequest.getRoleName(), roleCreateRequest.getDescription());


            // Lưu role postgresql
            var savedRole = roleDomainService
                    .createRole(
                            roleCreateRequest.getRoleName(),
                            roleCreateRequest.getDescription()
                    );

            return roleAppMapper.toResponse(savedRole);
        } catch (Exception e) {
            // Rollback keycloak nếu có lỗi
            keycloakRoleService.deleteRole(roleCreateRequest.getRoleName());
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN') && @roleEvaluator.hasHigherRole(#userId)")
    public UserRoleResponse addRoleForUser(UUID userId, String roleName) {
        var userDomain = userRepository.findById(userId, true).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        var roleDomain = roleRepository.findById(roleName).orElseThrow(
                () -> new RuntimeException("Role not found")
        );

        userDomain.getRoles().add(roleDomain);

        userRepository.save(userDomain);

        return userAppMapper.toUserRoleResponse(userDomain);
    }


    @Transactional
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN') && @roleEvaluator.hasHigherRole(#userId)")
    public UserRoleResponse removeRoleForUser(UUID userId, String roleName) {
        var userDomain = userRepository.findById(userId, true).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED, userId.toString())
        );

        boolean roleExists = userDomain.getRoles().stream()
                .anyMatch(role -> role.getId().equals(roleName));

        if (!roleExists) {
            throw new AppException(ErrorCode.ROLE_REMOVED_FAILED, roleName);
        }

        userDomain.getRoles().removeIf(role -> role.getId().equals(roleName));

        userRepository.save(userDomain);

        return userAppMapper.toUserRoleResponse(userDomain);
    }






}
