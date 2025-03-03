package com.roomx.application.service.user;

import com.roomx.application.dto.user.request.UserCreateRequest;
import com.roomx.application.dto.user.request.UserQueryFilterRequest;
import com.roomx.application.dto.user.response.UserCreateResponse;
import com.roomx.application.dto.user.response.UserInfoReponse;
import com.roomx.application.dto.user.response.UserPageResponse;
import com.roomx.application.dto.user.response.UserResponse;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.enums.UserType;
import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.multitenancy.keycloak.service.impl.KeycloakRoleServiceImpl;
import com.roomx.infrastructure.multitenancy.keycloak.service.impl.KeycloakUserServiceImpl;
import com.roomx.infrastructure.multitenancy.persistence.dto.UserFilter;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.page.UserEntityQueryRepository;
import com.roomx.infrastructure.multitenancy.persistence.specification.UserSpecification;
import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import com.roomx.shared.exception.AppException;
import com.roomx.shared.exception.ErrorCode;
import com.roomx.shared.exception.KeycloakNotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAppService{

    private final KeycloakUserServiceImpl keycloakUserServiceImpl;
    private final UserRepository userRepository;
    private final Keycloak keycloak;
    private final UserEntityQueryRepository userEntityQueryRepository;
    private final KeycloakRoleServiceImpl keycloakRoleServiceImpl;

    public UserInfoReponse getUserInfo() {
        return null;
    }

    public UserPageResponse getListUserPages(UserQueryFilterRequest filterRequest, int page, int size, String sortBy, String direction) {
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
        var userEntities = userEntityQueryRepository.findAll(specification, pageable);

        List<UserResponse> users = userEntities.getContent().stream()
                .map(entity -> UserResponse.builder()
                        .userId(entity.getId().toString())
                        .userCode(entity.getUserCode())
                        .email(entity.getEmail())
                        .userType(entity.getUserType())
                        .build()
                ).toList();

        return UserPageResponse.builder()
                .users(users)
                .totalPages(userEntities.getTotalPages())
                .totalElements(userEntities.getTotalElements())
                .currentPage(userEntities.getNumber())
                .pageSize(userEntities.getSize())
                .build();
    }

    @Transactional
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

        String userKeycloakId = null;

        try {
            // Tạo user trên Keycloak
            keycloakUserServiceImpl.save(userRepresentation);

            // Lấy user từ Keycloak
            var userKeycloakOpt = keycloakUserServiceImpl.findByUserCode(request.getUserCode());
            if (userKeycloakOpt.isEmpty()) {
                throw new KeycloakNotFoundException("User not found in Keycloak after creation.");
            }

            var userKeycloak = userKeycloakOpt.get();
            userKeycloakId = userKeycloak.getId();

            // Lưu user vào database
            var user = User.builder()
                    .id(UUID.fromString(userKeycloak.getId()))
                    .userCode(userKeycloak.getUsername())
                    .email(userKeycloak.getEmail())
                    .userType(UserType.EMPLOYEE.toString())
                    .build();

            userRepository.save(user);

            return UserCreateResponse.builder()
                    .userId(user.getId().toString())
                    .userCode(user.getUserCode())
                    .build();

        } catch (KeycloakNotFoundException ex) {
            // Nếu không tìm thấy user trong Keycloak sau khi tạo, ném lỗi ngay lập tức
            throw new AppException(ErrorCode.CREATE_USER_FAILED);
        } catch (RuntimeException ex) {
            // Nếu có lỗi khi lưu database, xóa user khỏi Keycloak để rollback
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
                .userId(userKeycloak.getId())
                .build();
    }

    public List<String> getTest() {

        return keycloakRoleServiceImpl.findAll();
    }
}
