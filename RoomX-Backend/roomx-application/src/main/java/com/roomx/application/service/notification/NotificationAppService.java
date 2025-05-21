package com.roomx.application.service.notification;

import com.roomx.domain.repository.UserRepository;
import com.roomx.infrastructure.cache.redis.service.RedisFcmTokenService;
import com.roomx.infrastructure.firebase.FCMNotificationService;
import com.roomx.infrastructure.notification.EmailService;
import com.roomx.shared.enums.EmailTemplateType;
import com.roomx.shared.event.BookingInfoEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationAppService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final RedisFcmTokenService redisFcmTokenService;

    private final FCMNotificationService fcmNotificationService;


    @KafkaListener(topics = "meeting-event-topic", groupId = "notification-service")
    public void handleMeetingEvent(BookingInfoEmailEvent event) {

        var listToken = redisFcmTokenService.getObjectSet("fcm_token:user:" + event.getUserId(), String.class);
        if (listToken == null || listToken.isEmpty()) {
            for (String token : listToken) {
                boolean success = fcmNotificationService.sendNotificationData(
                        token,
                        event.getMeetingTitle(),
                        "Bạn có một cập nhật về cuộc họp.",
                        event
                );
                if (!success) {
                    redisFcmTokenService.removeObjectFromSet("fcm_token:user:" + event.getUserId(), token);
                }
            }
        }

        String subject;
        EmailTemplateType template;

        switch (event.getEmailType()) {
            case APPROVAL_CONFLICT_BOOKING -> {
                subject = "Xác nhận xung đột cuộc họp";
                template = EmailTemplateType.APPROVAL_CONFLICT_BOOKING;
            }

            case ACCEPT_CONFLICT_BOOKING -> {
                subject = "Xác nhận thay đổi xung đột";
                template = EmailTemplateType.ACCEPT_CONFLICT_BOOKING;
            }
            case CONFIRM_MEETING -> {
                subject = "Lịch đặt đã được xác nhận";
                template = EmailTemplateType.CONFIRM_MEETING;
            }
            case UPDATE_MEETING -> {
                subject = "Cuộc họp đã thay đổi";
                template = EmailTemplateType.UPDATE_MEETING;
            }
            case REMINDER_MEETING_TODAY -> {
                subject = "Cuộc họp diễn ra hôm nay";
                template = EmailTemplateType.REMINDER_MEETING_TODAY;
            }
            case MEETING_AFTER_TIME -> {
                subject = "Cuộc họp diễn ra sau 30 phút";
                template = EmailTemplateType.MEETING_AFTER_TIME;
            }
            default -> {
                log.warn("Không xác định được loại sự kiện: {}", event.getEmailType());
                return;
            }
        }

        emailService.sendHtmlEmail(
                event.getToEmail(),
                subject,
                template,
                Map.ofEntries(
                        Map.entry("time", "30 phút"),
                        Map.entry("participantName", event.getParticipantName()),
                        Map.entry("meetingDate", event.getMeetingDate()),
                        Map.entry("meetingStart", event.getMeetingStart()),
                        Map.entry("meetingEnd", event.getMeetingEnd()),
                        Map.entry("branchName", event.getBranchName()),
                        Map.entry("roomName", event.getRoomName()),
                        Map.entry("meetingDuration", event.getDuration()),
                        Map.entry("meetingLocation", event.getMeetingLocation()),
                        Map.entry("meetingPurpose", event.getMeetingTitle()),
                        Map.entry("meetingDescription", event.getMeetingDescription()),
                        Map.entry("contactEmail", event.getFromEmail())
                )
        );
    }

   /* @KafkaListener(topics = "approve-booking-event-topic", groupId = "notification-service")
    public void handleApproveBookingEvent(BookingInfoEmailEvent event) {




        var listToken = redisFcmTokenService.getObjectSet("fcm_token:user:" + event.getUserId(), String.class);
        if (listToken == null || listToken.isEmpty()) {
            for (String token : listToken) {
                boolean success = fcmNotificationService.sendNotificationData(
                        token,
                        event.getMeetingTitle(),
                        "Bạn có cuộc họp mới.",
                        event
                );
                if (!success) {
                    redisFcmTokenService.removeObjectFromSet("fcm_token:user:" + event.getUserId(), token);
                }
            }
        }

        String subject;
        EmailTemplateType template;

        switch (event.getEmailType()) {
            case APPROVAL_CONFLICT_BOOKING -> {
                subject = "Xác nhận xung đột cuộc họp";
                template = EmailTemplateType.APPROVAL_CONFLICT_BOOKING;
            }

            case ACCEPT_CONFLICT_BOOKING -> {
                subject = "Xác nhận thay đổi xung đột";
                template = EmailTemplateType.ACCEPT_CONFLICT_BOOKING;
            }
            case CONFIRM_MEETING -> {
                subject = "Lịch đặt đã được xác nhận";
                template = EmailTemplateType.CONFIRM_MEETING;
            }
            case UPDATE_MEETING -> {
                subject = "Cuộc họp đã thay đổi";
                template = EmailTemplateType.UPDATE_MEETING;
            }
            case REMINDER_MEETING_TODAY -> {
                subject = "Cuộc họp diễn ra hôm nay";
                template = EmailTemplateType.REMINDER_MEETING_TODAY;
            }
            case MEETING_AFTER_TIME -> {
                subject = "Cuộc họp diễn ra sau 30 phút";
                template = EmailTemplateType.MEETING_AFTER_TIME;
            }
            default -> {
                log.warn("Không xác định được loại sự kiện: {}", event.getEmailType());
                return;
            }
        }

        emailService.sendHtmlEmail(
                event.getToEmail(),
                subject,
                template,
                Map.ofEntries(
                        Map.entry("time", "30 phút"),
                        Map.entry("participantName", event.getParticipantName()),
                        Map.entry("meetingDate", event.getMeetingDate()),
                        Map.entry("meetingStart", event.getMeetingStart()),
                        Map.entry("meetingEnd", event.getMeetingEnd()),
                        Map.entry("branchName", event.getBranchName()),
                        Map.entry("roomName", event.getRoomName()),
                        Map.entry("meetingDuration", event.getDuration()),
                        Map.entry("meetingLocation", event.getMeetingLocation()),
                        Map.entry("meetingPurpose", event.getMeetingTitle()),
                        Map.entry("meetingDescription", event.getMeetingDescription()),
                        Map.entry("contactEmail", event.getFromEmail())
                )
        );
    }*/
}
