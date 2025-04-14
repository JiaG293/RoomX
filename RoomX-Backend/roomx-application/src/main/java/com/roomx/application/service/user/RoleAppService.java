package com.roomx.application.service.user;

import com.roomx.infrastructure.keycloak.service.impl.KeycloakUserServiceImpl;
import com.roomx.shared.dto.user.request.RoleCreateRequest;
import com.roomx.shared.dto.user.response.RoleInfoResponse;
import com.roomx.shared.dto.user.response.UserRoleResponse;
import com.roomx.application.mapper.RoleAppMapper;
import com.roomx.application.mapper.UserAppMapper;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.domain.repository.UserRoleRepository;
import com.roomx.domain.service.RoleDomainService;
import com.roomx.infrastructure.keycloak.service.KeycloakRoleService;
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
    private final KeycloakUserServiceImpl keycloakUserService;

    public List<RoleInfoResponse> findAllRole(){
        return roleRepository.findAll().stream().map(roleAppMapper::toResponse).toList();
    }

    public List<RoleInfoResponse> findRolesNotAssignedToUser(String userId){
        return userRoleRepository.findRolesByUserId(UUID.fromString(userId)).stream().map(roleAppMapper::toResponse).collect(Collectors.toList());
    };


    @Transactional
    @PreAuthorize("hasRole('OWNER')")
    public RoleInfoResponse createRole(RoleCreateRequest roleCreateRequest) {

        try {

            keycloakRoleService.createRole(roleCreateRequest.getRoleName(), roleCreateRequest.getDescription());


            var savedRole = roleDomainService
                    .createRole(
                            roleCreateRequest.getRoleName(),
                            roleCreateRequest.getDescription()
                    );

            return roleAppMapper.toResponse(savedRole);
        } catch (Exception e) {
            keycloakRoleService.deleteRole(roleCreateRequest.getRoleName());
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('OWNER') || hasRole('ADMIN') && @roleEvaluator.hasHigherRole(#userId)")
    public UserRoleResponse addRoleForUser(UUID userId, String roleName) {
        var userDomain = userRepository.findById(userId, true).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED, null, userId)
        );

        var roleDomain = roleRepository.findById(roleName).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_FOUND, null, roleName)
        );


        try {
            keycloakUserService.addRole(userId.toString(), roleName);
        }catch (Exception e) {
            throw new AppException(ErrorCode.ROLE_CREATE_FAILED, null, "keycloak");
        }
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

        try {
            keycloakUserService.removeRole(userId.toString(), roleName);
        }catch (Exception e) {
            throw new AppException(ErrorCode.ROLE_REMOVED_FAILED, null, "keycloak");
        }

        userDomain.getRoles().removeIf(role -> role.getId().equals(roleName));

        userRepository.save(userDomain);

        return userAppMapper.toUserRoleResponse(userDomain);
    }






}
