package com.roomx.application.service.admin.impl;


import com.roomx.application.dto.request.UserCreationRequest;
import com.roomx.application.dto.response.UserResponse;
import com.roomx.application.exception.AppException;
import com.roomx.application.exception.ErrorCode;
import com.roomx.application.mapper.UserDomainMapper;
import com.roomx.infrastructure.keycloak.repository.UserRepresentationRepository;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeycloakUserService {

    Keycloak keycloak;

    UserRepresentationRepository userRepresentationRepository;

    UserDomainMapper userDomainMapper;

    @NonFinal
    @Value("${keycloak.realm}")
    String realm;


    public UserResponse createUser(UserCreationRequest request){


        /*// Tạo user mới trong Keycloak
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);

        // Tạo mật khẩu cho user
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());

        user.setCredentials(Collections.singletonList(passwordCred));

        // Gửi request tạo user
        keycloakAdminClient.realm(realm).users().create(user);
        return "ok";*/

        var realmRoles = keycloak.realm(realm).clients().findByClientId("roomx-tenantId-idp").getFirst();

        var roleRes = keycloak.realm(realm).clients().get(realmRoles.getId());

        var listRoles = roleRes.roles().list();


        log.info("realmRoles: {}", listRoles);

        //Kiểm tra maNhanVien và email có tồn tại chưa
        /*var checkMaNhanVien = userRepresentationRepository.findById(realm, request.getUsername());
        var checkEmail = userRepresentationRepository.findByEmail(realm, request.getEmail());

        log.info("Kiem tra ma nhan vien: {}", checkMaNhanVien.isPresent());
        if(checkMaNhanVien.isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        log.info("Kiem tra email: {}", checkEmail.isPresent());
        if(checkEmail.isPresent()){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }*/

        var user = userDomainMapper.toUserDomain(request);

        // Lưu người dùng vào keycloak
        userRepresentationRepository.save(realm, user);
        return userDomainMapper.toUserResponse(user);

    }
}
