package com.roomx.infrastructure.firebase.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.roomx.infrastructure.firebase.FCMNotificationService;
import com.roomx.shared.event.BookingInfoEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FCMNotificationServiceImpl implements FCMNotificationService {

    @Override
    public void sendNotification(String token, String title, String body) {

        var notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();


        var message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
           log.info("Successfully sent message: {}", response);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    @Override
    public boolean sendNotificationData(String targetToken, String title, String body, BookingInfoEmailEvent event) {

        var dataMap = new HashMap<String, String>();
        dataMap.put("id", event.getId());
        dataMap.put("type", event.getEmailType().getEnum());
        dataMap.put("bookingId", event.getBookingId());
        dataMap.put("branchName", event.getBranchName());
        dataMap.put("roomName", event.getRoomName());
        dataMap.put("meetingDate", event.getMeetingDate().toString());
        dataMap.put("meetingStart", event.getMeetingStart().toString());
        dataMap.put("meetingEnd", event.getMeetingEnd().toString());
        dataMap.put("duration", event.getDuration());
        dataMap.put("createdAt", LocalDate.now().toString());

        var notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();


        var message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .putAllData(dataMap)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message: {}", response);
            return true;
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            return false;
        }
    }
}
