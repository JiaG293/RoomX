package com.roomx.application.service.user;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.repository.UserRoleRepository;
import com.roomx.infrastructure.persistence.repository.impl.UserRoleEntityRepository;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import com.roomx.shared.dto.user.request.UserCreateRequest;
import com.roomx.shared.dto.user.request.UserQueryFilterRequest;
import com.roomx.shared.dto.user.response.UserCreateResponse;
import com.roomx.shared.dto.user.response.UserInfoReponse;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.application.mapper.UserAppMapper;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.RoleType;
import com.roomx.shared.enums.UserType;
import com.roomx.domain.repository.RoleRepository;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.keycloak.service.impl.KeycloakRoleServiceImpl;
import com.roomx.infrastructure.keycloak.service.impl.KeycloakUserServiceImpl;
import com.roomx.infrastructure.persistence.dto.UserFilter;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;
import com.roomx.infrastructure.persistence.repository.specification.UserEntitySpecRepository;
import com.roomx.infrastructure.persistence.repository.specification.UserSpecification;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.KeycloakNotFoundException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAppService {

    private final KeycloakUserServiceImpl keycloakUserServiceImpl;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntitySpecRepository userEntitySpecRepository;
    private final UserAppMapper userAppMapper;
    private final SecurityUtil securityUtil;
    private final UserRoleEntityRepository userRoleEntityRepository;
    private final UserRoleRepository userRoleRepository;


    @PreAuthorize("@roleEvaluator.hasHigherRole(#userId)")
    public UserInfoReponse getDetailUser(String userId) {
        var userDomain = userRepository.findById(UUID.fromString(userId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED, null, userId));

        return userAppMapper.toResponseInfo(userDomain);
    }

    public UserInfoReponse getUserInfo() {

        var userId = securityUtil.getCurrentUserId();

        var userDomain = userRepository.findById(UUID.fromString(userId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED, null, userId));

        log.info(userDomain.getRoles().toString());

        return userAppMapper.toResponseInfo(userDomain);
    }

    @PreAuthorize("@roleEvaluator.hasAnyRoleType('approve')")
    public Page<UserResponse> getListUserPages(UserQueryFilterRequest filterRequest, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<UserEntity> specification = UserSpecification.filterUsers(
                UserFilter.builder()
                        .email(filterRequest.getEmail())
                        .userType(filterRequest.getUserType())
                        .userId(filterRequest.getUserId())
                        .userCode(filterRequest.getUserCode())
                        .build()
        );
        var userEntities = userEntitySpecRepository.findAll(specification, pageable);

        return userEntities.map(userAppMapper::toUserResponse);
    }


    public UserCreateResponse createUser(UserCreateRequest request) {
        if (keycloakUserServiceImpl.findUserByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (keycloakUserServiceImpl.findByUserCode(request.getUserCode()).isPresent()) {
            throw new AppException(ErrorCode.EMPLOYEE_ID_EXISTED);
        }

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUserCode());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        userRepresentation.setCredentials(List.of(credential));
        userRepresentation.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();

        var roles = Optional.ofNullable(request.getRoles())
                .filter(roleIds -> !roleIds.isEmpty())
                .map(roleIds -> roleIds.stream()
                        .map(roleRepository::findById)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toSet())
                ).orElseGet(() -> roleRepository.findById(RoleType.USER.toString())
                        .map(Set::of)
                        .orElseGet(HashSet::new)
                );

        if(roles.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND, null, request.getRoles());
        }

        attributes.put("roles", roles.stream().map(Role::getId).toList());

        userRepresentation.setAttributes(attributes);

        String userKeycloakId = null;

        try {
            keycloakUserServiceImpl.save(userRepresentation);

            var userKeycloakOpt = keycloakUserServiceImpl.findByUserCode(request.getUserCode());
            if (userKeycloakOpt.isEmpty()) {
                throw new KeycloakNotFoundException("User not found in Keycloak after creation.");
            }

            var userKeycloak = userKeycloakOpt.get();
            userKeycloakId = userKeycloak.getId();


            var user = User.builder()
                    .id(UUID.fromString(userKeycloak.getId()))
                    .userCode(userKeycloak.getUsername())
                    .email(userKeycloak.getEmail())
                    .userType(request.getType() != null ? request.getType() : UserType.getDefault().toString())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .phoneNumber(request.getPhoneNumber())
                    .gender(request.isGender())
                    .avatarImage(request.getAvatarImage())
                    .roles(roles)
                    .build();

            userRepository.save(user);

            return UserCreateResponse.builder()
                    .id(user.getId().toString())
                    .userCode(user.getUserCode())
                    .build();

        } catch (KeycloakNotFoundException ex) {
            throw new AppException(ErrorCode.CREATE_USER_FAILED);
        } catch (Exception ex) {
            if (userKeycloakId != null) {
                try {
                    keycloakUserServiceImpl.delete(userKeycloakId);
                } catch (Exception deleteEx) {
                    throw new AppException(ErrorCode.ROLLBACK_FAILED);
                }
            }
            throw new AppException(ErrorCode.CREATE_USER_FAILED);
        }
    }

    public String getTenant() {
        return TenantContextHolder.getTenantIdentifier();
    }

    public UserResponse getUserDetail(String data) {
        var userKeycloak = keycloakUserServiceImpl.findByUserCode(data)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));

        var userRepresentation = new UserRepresentation();

        return UserResponse.builder()
                .userCode(userKeycloak.getUsername())
                .id(userKeycloak.getId())
                .build();
    }


    public void hardDeleteUser(String userId) {
        try {
            keycloakUserServiceImpl.delete(userId);
        } catch (Exception e) {
            log.warn("Không thể xóa user trên Keycloak: {}", userId, e);
        }

        try {
            userRepository.deleteUserRole(userId);
            userRepository.delete(userId);
        } catch (Exception e) {
            log.warn("Không thể xóa user trong database: {}", userId, e);
        }
    }

    public UserResponse activeUser(String userId) {
        return null;
    }

    public void softDeleteUser(String userId) {
        var userDomain = userRepository.findById(UUID.fromString(userId), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED, null, userId));

        userDomain.setStatus(DeleteStatusType.INACTIVE.toString());
        userRepository.save(userDomain);

        var userKeycloakOpt = keycloakUserServiceImpl.findByUserCode(userDomain.getUserCode());
        if (userKeycloakOpt.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED, null, userId);
        }
        keycloakUserServiceImpl.changeStatusUser(userId, false);
    }


    public User findApproverWithFree() {
        var listUserRoleApproverDomain = userRepository.findAllUserWithRole(RoleType.APPROVER.toString());
        //Implement logic assign approver free
        Random random = new Random();
        return listUserRoleApproverDomain.get(random.nextInt(listUserRoleApproverDomain.size()));
    }
}
