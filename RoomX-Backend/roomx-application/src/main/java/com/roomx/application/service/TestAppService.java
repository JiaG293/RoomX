package com.roomx.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.application.service.booking.RoomSchedulerAppService;
import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.repository.*;
import com.roomx.infrastructure.cache.redis.service.RedisFcmTokenService;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.distributed.kafka.config.KafkaTenantService;
import com.roomx.infrastructure.firebase.FCMNotificationService;
import com.roomx.infrastructure.minio.MinioService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.infrastructure.multitenancy.hibernate.TenantIdentifierResolver;
import com.roomx.infrastructure.notification.EmailService;
import com.roomx.infrastructure.persistence.dto.RoomClassFilter;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.mapper.RoomEntityMapper;
import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import com.roomx.infrastructure.persistence.repository.jpa.*;
import com.roomx.infrastructure.persistence.service.ApprovalFormEntityService;
import com.roomx.infrastructure.persistence.service.BookingEntityService;
import com.roomx.infrastructure.persistence.service.RoomEntityService;
import com.roomx.infrastructure.security.oauth.RoleEvaluator;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import com.roomx.shared.base.MeetingMessage;
import com.roomx.shared.enums.*;
import com.roomx.shared.event.ApproveBookingEvent;
import com.roomx.shared.event.BookingInfoEmailEvent;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestAppService {
    private final RedisTenantService redisTenantService;
    private final MinioService minioService;
    private final RoleEvaluator roleEvaluator;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTenantService kafkaTenantService;
    private final RoomSchedulerAppService roomSchedulerAppService;
    private final DateRequestExceptionRepository dateRequestExceptionRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final ApprovalFormRepository approvalFormRepository;
    private final ApprovalFormEntityService approvalFormEntityService;
    private final BookingRepository bookingRepository;
    private final BookingEntityService bookingEntityService;
    private final EmailService emailService;
    private final RoomRepository roomRepository;
    private final RedisFcmTokenService redisFcmTokenService;
    private final FCMNotificationService fcmNotificationService;
    private final SecurityUtil securityUtil;
    private final JpaApprovalFormEntityRepository jpaApprovalFormEntityRepository;
    private final RoomEntityService roomEntityService;
    private final JpaRoomClassPriceHistoryEntityRepository jpaRoomClassPriceHistoryEntityRepository;
    private final JpaRoomEntityRepository jpaRoomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;
    private final JpaRoomClassEntityRepository jpaRoomClassEntityRepository;
    private final JpaGroupEntityRepository jpaGroupEntityRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final UserRepository userRepository;


    public Object testAppService() {

        /*var map = new HashMap<String, List<String>>();
        map.put("1", List.of("value1", "value2"));
        map.put("2", List.of("value1", "value2"));

        var key = "test" + UUID.randomUUID().toString();
        redisTenantService.put(key, map, 30, TimeUnit.SECONDS);

        var result = redisTenantService.getObject(key, HashMap.class);*/

//        redisTenantService.put("helo", "12", 30, TimeUnit.SECONDS);

        Sort sort = "desc".equalsIgnoreCase("desc")
                ? Sort.by("status").descending()
                : Sort.by("status").ascending();
        ZoneId zoneId = ZoneId.systemDefault();
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        /*var result = bookingEntityService.findBookingsByTimeRangeAndUserId(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 1),
                "ea4e9c4c-a317-4064-8a5b-da2b339e4380",
                pageable
        );*/

//        var result = jpaApprovalFormEntityRepository.test(
////                List.of("APPROVED"),
////                List.of(),
//                ApprovalStatusType.getList(),
//                LocalDate.of(2025, 4, 1).atStartOfDay(zoneId).toInstant(),
//                LocalDate.of(2025, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant(),
//                UUID.fromString("3393e980-0503-454a-94ef-e43258639994"),
//                pageable
//        );
//        var result = approvalFormEntityService.findAllByStatusAndTimeRangeWithBookingRequest(
//                ApprovalStatusType.getList(),
//                LocalDate.of(2025, 4, 1).atStartOfDay(zoneId).toInstant(),
//                LocalDate.of(2025, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant(),
////                "3393e980-0503-454a-94ef-e43258639994",
//                null,
//                pageable
//        );
//        var result = approvalFormRepository
//                .findAllByStatusAndTimeRangeWithBookingRequest(
//                        ApprovalStatusType.getListCanApproval(),
//                        LocalDate.of(2025, 4, 1).atStartOfDay(zoneId).toInstant(),
//                        LocalDate.of(2025, 6, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant(),
//                        null
//                );

//        var result = roomEntityService.filterSearchPageRooms(
//                RoomFilter.builder()
//                        .build(),
//                pageable
//        );

        RoomClassFilter roomFilter = new RoomClassFilter();
        var result = jpaGroupEntityRepository
                .findByIdDetail(UUID.fromString("d17b756f-1d29-481b-99a8-0937a88d3756"),
                        DeleteStatusType.ACTIVE.toString()).get().getMembers();

        return result;
    }


    public Object testMinio(List<MultipartFile> request, boolean makePrivate, String path) {
        var result = new ArrayList<String>();

        request.forEach(file -> {
            result.add(minioService.uploadFile(file, path, makePrivate, 7, TimeUnit.DAYS, null));
        });


        return result;
    }


    public Object testKafka(String data) {
        /*kafkaTemplate.send(
                MessageBuilder.withPayload(RoomFilter.builder().id("lkfjslkdfjsf").build())
                        .setHeader(KafkaHeaders.TOPIC, "topic-a")
                        .setHeader("tenant-id", TenantContextHolder.getTenantIdentifier())
                        .build()
        );*/
        /*kafkaTemplate.send(
                MessageBuilder.withPayload(
                                RoomFilter.builder()
                                        .id(data)

                                        .build()
                        ).setHeader(KafkaHeaders.TOPIC, "topic-a")
                        .setHeader("tenant-id", TenantContextHolder.getRequiredTenantIdentifier())
                        .build()
        );*/

        var approveBookingEvent = ApproveBookingEvent.builder()
                .id(UUID.randomUUID().toString())
                .bookingRequestId("1d267859-f9fa-4159-b6cc-a23c5beff10e")
                .ownerId("3393e980-0503-454a-94ef-e43258639994")
                .participants(List.of(
                        "nam@jiag.id.vn",
                        "hiep@jiag.id.vn",
                        "sang@jiag.id.vn"
                )).tenantId(tenantIdentifierResolver.resolveCurrentTenantIdentifier())
                .build();


        kafkaTemplate.send("approve-booking-event-topic", approveBookingEvent);
        return "1";
    }

    @KafkaListener(topics = "topic-a", groupId = "group-test")
    public Object testKafka(
            @Payload RoomFilter message,
            @Header(name = "tenant-id", required = false) String tenantId
    ) {

        System.out.println(String.format("\n\n\n Message: %s \nTenant ID: %s \n\n\n", message, tenantId));
        return "";
    }

    @KafkaListener(topics = "approve-booking-event-topic", groupId = "notification-service")
    public Object testKafka(
           ApproveBookingEvent event
    ) {

        System.out.println(String.format("\n\n\n Message: %s \nTenant ID: %s \n\n\n", event, event.getTenantId()));
        var bookingRequestDomain = bookingRequestRepository.findById(event.getBookingRequestId())
                .orElseThrow(() -> {
                    log.error("Không tìm thấy booking request {}", event.getId());
                    return new AppException(ErrorCode.BOOKING_REQUEST_NOT_FOUND);
                });
        var userDomain = userRepository.findById(UUID.fromString(event.getOwnerId()), true).orElse(null);


        var listToken = redisFcmTokenService.getObjectSet("fcm_token:user:" + event.getId(), String.class);
        if (listToken == null || listToken.isEmpty() || bookingRequestDomain == null) {
            for (String token : listToken) {
                boolean success = fcmNotificationService.sendApproveNotificationData(
                        token,
                        bookingRequestDomain.getTitle(),
                        "Cuộc họp đã được phê duyệt.",
                        bookingRequestDomain
                );
                if (!success) {
                    redisFcmTokenService.removeObjectFromSet("fcm_token:user:" + event.getId(), token);
                }
            }

           /* emailService.sendHtmlEmail(
                    userDomain.getEmail(),
                    "Lịch đặt đã được xác nhận",
                    EmailTemplateType.CONFIRM_MEETING,
                    Map.ofEntries(
                            Map.entry("time", "30 phút"),
                            Map.entry("participantName", userDomain.getFirstName() + " " + userDomain.getLastName()),
                            Map.entry("meetingDate", bookingRequestDomain.getStartDate()),
                            Map.entry("meetingStart", bookingRequestDomain.getStartDate()),
                            Map.entry("meetingEnd", bookingRequestDomain.getEndDate()),
                            Map.entry("branchName", bookingRequestDomain.getBranchName()),
                            Map.entry("roomName", bookingRequestDomain.getRoomName()),
                            Map.entry("meetingDuration", bookingRequestDomain.getDuration()),
                            Map.entry("meetingLocation", bookingRequestDomain.getMeetingLocation()),
                            Map.entry("meetingPurpose", bookingRequestDomain.getMeetingTitle()),
                            Map.entry("meetingDescription", bookingRequestDomain.getMeetingDescription()),
                            Map.entry("contactEmail", "asgy2002@gmail.com")
                    )
            );*/
        }


        return "";
    }


    public void testKafka1(MeetingMessage request) {
        kafkaTenantService.sendMessage(MeetingMessage.builder()
                .message(request.getMessage())
                .senderId(UUID.randomUUID().toString())
                .title(request.getTitle())
                .recipientId(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build(), "create-notification-topic");
    }

    public void mail() {

        String targetToken = "cQS43a8aCHFf9XaSlE43qu:APA91bEouduGtKTeJENftKmzgp1HAGSynwtOc0Jt5k1fkbwg1rPkVu9oD0t314cZpzThRhr05YrTc1flWvtrn1KO8fcyy1HhGu_hTGZqaDdiNytmPYk8IMQ";
        String userId = securityUtil.getCurrentUserId();
        redisFcmTokenService.addObjectToSet("fcm_token:user:" + userId, targetToken);
        var bookingEvent = BookingInfoEmailEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .bookingId(UUID.randomUUID().toString())
                .bookingRequestId(UUID.randomUUID().toString())
                .userId(userId)
                .participantName("Nguyễn Văn A")
                .toEmail("asgy2002@gmail.com")
                .fromEmail("asgy2002@gmail.com")
                .branchName("Chi nhánh HCM")
                .roomName("B45.341")
                .meetingDate(LocalDate.of(2025, 10, 22))
                .meetingLocation("Chi nhánh DN, tòa nhà B, tầng 25, phòng b45.341")
                .meetingStart(LocalTime.of(10, 0))
                .meetingEnd(LocalTime.of(11, 0))
                .meetingTitle("purpose la kfjdalk jflaskdf asfd asfd as")
                .meetingDescription("fklsjflksjfl ksjdf")
                .meetingTitle("Testing title")
                .emailType(EmailTemplateType.CONFIRM_MEETING)
                .build();

        fcmNotificationService.sendNotificationData(targetToken, "helo", "hi", bookingEvent);

        kafkaTenantService.sendMessage(
                bookingEvent,
                "meeting-event-topic"
        );


    }


}
