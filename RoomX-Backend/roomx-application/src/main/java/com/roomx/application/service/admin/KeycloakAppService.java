package com.roomx.application.service.admin;

import com.roomx.infrastructure.keycloak.service.KeycloakRealmService;
import com.roomx.shared.exception.KeycloakAdminException;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.RealmAlreadyExistsException;
import com.roomx.shared.exception.exception.RealmNotFoundException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAppService {
    private final KeycloakRealmService keycloakRealmService;

    public Object initRealmNewUser(String realmName) {
        try {
            keycloakRealmService.copyRealm("giau", realmName);
        } catch (KeycloakAdminException kae) {
            throw new AppException(ErrorCode.KEYCLOAK_REALM_BAD_REQUEST, kae.getMessage());
        } catch (RealmAlreadyExistsException raee) {
            throw new AppException(ErrorCode.KEYCLOAK_REALM_CONFLICT, raee.getMessage());
        } catch (RealmNotFoundException rnfe) {
            throw new AppException(ErrorCode.KEYCLOAK_REALM_NOT_FOUND, rnfe.getMessage());
        }
        return Map.of(
                "username", "admin",
                "password", "admin"
        );
    }
}
