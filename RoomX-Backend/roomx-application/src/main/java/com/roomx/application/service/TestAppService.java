package com.roomx.application.service;

import com.roomx.application.service.booking.RoomSchedulerAppService;
import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.repository.*;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.distributed.kafka.config.KafkaTenantService;
import com.roomx.infrastructure.minio.MinioService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.infrastructure.notification.EmailService;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomEntityRepository;
import com.roomx.infrastructure.persistence.service.ApprovalFormEntityService;
import com.roomx.infrastructure.persistence.service.BookingEntityService;
import com.roomx.infrastructure.security.oauth.RoleEvaluator;
import com.roomx.shared.base.MeetingMessage;
import com.roomx.shared.enums.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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


    public Object testAppService() {

        /*var map = new HashMap<String, List<String>>();
        map.put("1", List.of("value1", "value2"));
        map.put("2", List.of("value1", "value2"));

        var key = "test" + UUID.randomUUID().toString();
        redisTenantService.put(key, map, 30, TimeUnit.SECONDS);

        var result = redisTenantService.getObject(key, HashMap.class);*/

//        redisTenantService.put("helo", "12", 30, TimeUnit.SECONDS);

        /*Sort sort = "desc".equalsIgnoreCase("desc")
                ? Sort.by("id").descending()
                : Sort.by("id").ascending();
        ZoneId zoneId = ZoneId.systemDefault();
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        var result = bookingEntityService.findBookingsByTimeRangeAndUserId(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 1),
                "ea4e9c4c-a317-4064-8a5b-da2b339e4380",
                pageable
        );*/

        var result = roomRepository
                .findAllByBranchIdAndStatusMinimum(
                        "65f1d8a0-6885-4b51-a691-f843b279dd8b",
                        RoomStatusType.AVAILABLE.toString()
                );
        return result;
    }

    public Object testMinio(List<MultipartFile> request, boolean makePrivate, String path) {
        var result = new ArrayList<String>();

        request.forEach(file -> {
            result.add(minioService.uploadFile(file, path, makePrivate, 7, TimeUnit.DAYS));
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
        kafkaTemplate.send(
                MessageBuilder.withPayload(
                                RoomFilter.builder()
                                        .id(data)
                                        .branchCode(data)
                                        .build()
                        ).setHeader(KafkaHeaders.TOPIC, "topic-a")
                        .setHeader("tenant-id", TenantContextHolder.getRequiredTenantIdentifier())
                        .build()
        );
        return data;
    }

    @KafkaListener(topics = "topic-a", groupId = "group-test")
    public Object testKafka(
            @Payload RoomFilter message,
            @Header(name = "tenant-id", required = false) String tenantId
    ) {

        System.out.println(String.format("\n\n\n Message: %s \nTenant ID: %s \n\n\n", message, tenantId));
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
        emailService
                .sendHtmlEmail("asgy2002@gmail.com",
                        "subject",
                        EmailTemplateType.UPDATE_MEETING,
                        Map.of("originalMeetingDate", "test"));
    }


}
