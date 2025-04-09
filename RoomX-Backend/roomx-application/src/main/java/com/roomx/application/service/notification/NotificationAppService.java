package com.roomx.application.service.notification;

import com.roomx.infrastructure.firebase.FCMNotificationService;
import com.roomx.infrastructure.firebase.impl.FCMNotificationServiceImpl;
import com.roomx.shared.base.MeetingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationAppService {
    private final FCMNotificationService fcmNotificationService;


    @KafkaListener(topics = "create-notification-topic", groupId = "default-group")
    public void sendNotification(
            @Payload MeetingMessage meetingMessage,
            @Header(name = "tenant-id", required = false) String tenantId
    ) {

        fcmNotificationService.sendNotification(
                UUID.randomUUID().toString(),
                meetingMessage.getTitle(),
                meetingMessage.getMessage()
        );
    }
}
