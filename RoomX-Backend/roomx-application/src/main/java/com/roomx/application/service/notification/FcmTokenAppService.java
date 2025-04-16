package com.roomx.application.service.notification;

import com.roomx.infrastructure.cache.redis.service.RedisFcmTokenService;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmTokenAppService {
    private final RedisFcmTokenService redisFcmTokenService;
    private final SecurityUtil securityUtil;

    public void addFcmToken(String fcmToken) {
        var userId = securityUtil.getCurrentUserId();
        redisFcmTokenService.addObjectToSet("fcm_token:user:"+ userId, fcmToken);
        log.info("add token success: {}", fcmToken);
    }

    public void removeFcmToken(String fcmToken) {
        var userId = securityUtil.getCurrentUserId();
        redisFcmTokenService.removeObjectFromSet("fcm_token:user:"+ userId, fcmToken);
        log.info("remove token success: {}", fcmToken);
    }
}
