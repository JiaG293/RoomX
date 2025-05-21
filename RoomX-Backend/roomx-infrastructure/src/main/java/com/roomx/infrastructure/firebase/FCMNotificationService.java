package com.roomx.infrastructure.firebase;

import com.roomx.shared.event.BookingInfoEmailEvent;

public interface FCMNotificationService {
    void sendNotification(String targetToken, String title, String body);
    boolean sendNotificationData(String targetToken, String title, String body, BookingInfoEmailEvent event);
    boolean sendApproveNotificationData(String targetToken, String title, String body, Object data);
}
