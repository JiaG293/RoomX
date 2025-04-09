package com.roomx.infrastructure.firebase;

public interface FCMNotificationService {
    void sendNotification(String targetToken, String title, String body);
}
