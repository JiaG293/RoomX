package com.roomx.infrastructure.keycloak.service.impl;


import com.roomx.infrastructure.keycloak.config.KeycloakConfig;
import com.roomx.infrastructure.keycloak.service.KeycloakRealmService;
import com.roomx.shared.exception.KeycloakAdminException;
import com.roomx.shared.exception.exception.RealmAlreadyExistsException;
import com.roomx.shared.exception.exception.RealmNotFoundException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakRealmServiceImpl implements KeycloakRealmService {
    private final Keycloak keycloak;

    private static final int REALM_READY_MAX_RETRIES = 5;
    private static final long REALM_READY_RETRY_DELAY_MS = 500;

    @Override
    public void copyRealm(String sourceRealmName, String newRealmName)
            throws RealmNotFoundException, KeycloakAdminException, RealmAlreadyExistsException { // Đảm bảo có RealmNotFoundException trong throws clause

        log.info("Bắt đầu quá trình sao chép realm '{}' thành '{}'", sourceRealmName, newRealmName);

        // Lấy cấu hình Realm
        RealmResource sourceRealmResource = keycloak.realm(sourceRealmName);
        RealmRepresentation sourceRealmRepresentation;
        try {
            log.debug("Đang lấy cấu hình từ realm nguồn: {}", sourceRealmName);
            sourceRealmRepresentation = sourceRealmResource.toRepresentation();
            log.info("Đã lấy thành công cấu hình từ realm: {}", sourceRealmName);
        } catch (Exception e) {
            log.error("Lỗi không mong muốn khi lấy realm nguồn {}: {}", sourceRealmName, e.getMessage(), e);
            throw new KeycloakAdminException("Lỗi khi lấy realm nguồn: " + e.getMessage(), e);
        }

        // Chỉnh sửa cấu hình cho Realm
        log.debug("Đang chỉnh sửa cấu hình cho realm mới: {}", newRealmName);
        RealmRepresentation newRealmRepresentation = sourceRealmRepresentation;
        newRealmRepresentation.setRealm(newRealmName);
        newRealmRepresentation.setId(null);
        String displayName = newRealmName.trim().toLowerCase();
        newRealmRepresentation.setDisplayName(displayName);
        log.debug("Đã cập nhật tên ID='{}', DisplayName='{}' và xóa ID nội bộ.", newRealmName, displayName);


        try {
            log.info("Đang tạo realm mới: {}", newRealmName);
            keycloak.realms().create(newRealmRepresentation);
            log.info("Lệnh tạo realm '{}' đã được gửi thành công. Đang chờ xác minh...", newRealmName);

            // Xác minh Realm mới đã sẵn sàng
            if (!isRealmReady(newRealmName)) {
                // Nếu không sẵn sàng sau các lần thử, ném lỗi
                log.error("Không thể xác minh realm '{}' sẵn sàng sau {} lần thử.", newRealmName, REALM_READY_MAX_RETRIES);
                throw new KeycloakAdminException("Realm '" + newRealmName + "' không sẵn sàng sau khi tạo.");
            }
            log.info("Realm '{}' đã được xác minh là sẵn sàng.", newRealmName);


            // Tạo user 'admin'
            createUserAdmin(newRealmName);


        } catch (ClientErrorException cee) {
            if (cee.getResponse().getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                log.warn("Realm '{}' đã tồn tại.", newRealmName);
                throw new RealmAlreadyExistsException("Realm '" + newRealmName + "' đã tồn tại.");
            } else {
                log.error("Lỗi ClientErrorException khi tạo realm {}: Status={}", newRealmName, cee.getResponse().getStatus(), cee);
                throw new KeycloakAdminException("Lỗi khi tạo realm: " + cee.getResponse().getStatusInfo().getReasonPhrase(), cee);
            }
        } catch (KeycloakAdminException kae) {

            throw kae;
        } catch (Exception e) {
            log.error("Lỗi không mong muốn trong quá trình tạo/kiểm tra realm {}: {}", newRealmName, e.getMessage(), e);
            throw new KeycloakAdminException("Lỗi không mong muốn khi tạo/kiểm tra realm: " + e.getMessage(), e);
        }

        log.info("Hoàn thành quá trình sao chép realm '{}' thành '{}' và tạo user admin.", sourceRealmName, newRealmName);
    }

    /**
     * Kiểm tra xem realm đã sẵn sàng để truy cập qua API chưa.
     * Thử lấy representation của realm với số lần thử và độ trễ nhất định.
     *
     * @param realmName Tên realm cần kiểm tra.
     * @return true nếu realm sẵn sàng, false nếu không.
     * @throws KeycloakAdminException Nếu thread bị ngắt khi đang chờ.
     */
    private boolean isRealmReady(String realmName) throws KeycloakAdminException {
        boolean realmReady = false;
        for (int i = 0; i < REALM_READY_MAX_RETRIES; i++) {
            try {
                log.debug("Kiểm tra realm '{}' lần {}...", realmName, i + 1);
                keycloak.realm(realmName).toRepresentation();
                realmReady = true;
                break;
            } catch (Exception verifyEx) {

                log.warn("Lỗi khi kiểm tra realm '{}': {}. Thử lại sau {}ms...", realmName, verifyEx.getMessage(), REALM_READY_RETRY_DELAY_MS);
            }


            if (i < REALM_READY_MAX_RETRIES - 1) {
                try {
                    Thread.sleep(REALM_READY_RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Thread bị ngắt khi đang chờ realm '{}' sẵn sàng.", realmName, ie);
                    throw new KeycloakAdminException("Bị ngắt khi đang chờ realm '" + realmName + "' sẵn sàng", ie);
                }
            }
        }
        return realmReady;
    }

    /**
     * Tạo user 'admin' với mật khẩu 'admin' trong realm được chỉ định.
     *
     * @param realmName Tên realm để tạo user.
     * @throws KeycloakAdminException Nếu có lỗi xảy ra trong quá trình tạo user.
     */
    private void createUserAdmin(String realmName) throws KeycloakAdminException {
        log.info("Bắt đầu tạo user 'admin' trong realm '{}'", realmName);
        UsersResource usersResource;
        try {
            usersResource = keycloak.realm(realmName).users();
        } catch (Exception ex) {
            log.error("Không thể lấy UsersResource cho realm '{}': {}", realmName, ex.getMessage(), ex);
            throw new KeycloakAdminException("Lỗi khi lấy tài nguyên user cho realm '" + realmName + "'.", ex);
        }

        UserRepresentation adminUser = new UserRepresentation();
        adminUser.setUsername("admin");
        adminUser.setEnabled(true);


        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue("admin");
        adminUser.setCredentials(Collections.singletonList(passwordCred));

        Response userCreationResponse = null;
        try {
            log.info("Thực hiện gọi API tạo user 'admin' cho realm '{}'...", realmName);
            userCreationResponse = usersResource.create(adminUser);

            log.info("Phản hồi tạo user: Status Family = {}, Status Code = {}",
                    userCreationResponse.getStatusInfo().getFamily(),
                    userCreationResponse.getStatus());

            if (userCreationResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String reason = "";
                if (userCreationResponse.hasEntity()) {
                    try {
                        reason = userCreationResponse.readEntity(String.class);
                    } catch (Exception readEx) {
                        log.warn("Không thể đọc entity lỗi từ phản hồi tạo user: {}", readEx.getMessage());
                        reason = "[Không thể đọc nội dung lỗi]";
                    }
                }
                log.error("Tạo user 'admin' trong realm '{}' thất bại. Status: {}, Reason: {}",
                        realmName, userCreationResponse.getStatus(), reason);

                throw new KeycloakAdminException("Tạo user 'admin' thất bại. Status: "
                        + userCreationResponse.getStatus() + ", Reason: " + reason);
            }

            log.info("Tạo user 'admin' thành công trong realm '{}'.", realmName);


        } catch (ClientErrorException userCee) {
            String errorDetails = "";
            if (userCee.getResponse() != null && userCee.getResponse().hasEntity()) {
                try {
                    errorDetails = userCee.getResponse().readEntity(String.class);
                } catch(Exception readEx) { /* Ignore */}
            }
            log.error("Lỗi ClientErrorException khi tạo user 'admin' trong realm '{}'. Status: {}, Details: {}",
                    realmName, userCee.getResponse().getStatus(), errorDetails, userCee);

            if (userCee.getResponse().getStatus() == Response.Status.CONFLICT.getStatusCode()) {

                throw new KeycloakAdminException("User 'admin' đã tồn tại hoặc xung đột chính sách trong realm '"
                        + realmName + "'. Details: " + errorDetails, userCee);
            } else {
                throw new KeycloakAdminException("Lỗi client khi tạo user 'admin': "
                        + userCee.getResponse().getStatusInfo().getReasonPhrase()
                        + ". Details: " + errorDetails, userCee);
            }
        } catch (Exception ex) {
            log.error("Lỗi không mong muốn khi tạo user 'admin' trong realm {}: {}", realmName, ex.getMessage(), ex);
            throw new KeycloakAdminException("Lỗi không mong muốn khi tạo user 'admin': " + ex.getMessage(), ex);
        } finally {

            if (userCreationResponse != null) {
                userCreationResponse.close();
                log.debug("Đã đóng userCreationResponse.");
            }
        }
    }
}
