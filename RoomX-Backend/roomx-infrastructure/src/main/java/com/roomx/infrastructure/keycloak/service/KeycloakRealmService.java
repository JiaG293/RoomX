package com.roomx.infrastructure.keycloak.service;

import com.roomx.shared.exception.KeycloakAdminException;
import com.roomx.shared.exception.exception.RealmAlreadyExistsException;
import com.roomx.shared.exception.exception.RealmNotFoundException;

public interface KeycloakRealmService {
    void copyRealm(String sourceRealmName, String newRealmName) throws RealmNotFoundException, KeycloakAdminException, RealmAlreadyExistsException;
}
