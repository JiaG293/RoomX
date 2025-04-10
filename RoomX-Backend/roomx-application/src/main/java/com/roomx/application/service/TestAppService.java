package com.roomx.application.service;

import com.roomx.application.service.booking.RoomSchedulerAppService;
import com.roomx.domain.repository.BookingRepository;
import com.roomx.domain.repository.BookingRequestRepository;
import com.roomx.domain.repository.DateRequestExceptionRepository;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.distributed.kafka.config.KafkaTenantService;
import com.roomx.infrastructure.minio.MinioService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.security.oauth.RoleEvaluator;
import com.roomx.shared.base.MeetingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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



    public Object testAppService() {

        /*var map = new HashMap<String, List<String>>();
        map.put("1", List.of("value1", "value2"));
        map.put("2", List.of("value1", "value2"));

        var key = "test" + UUID.randomUUID().toString();
        redisTenantService.put(key, map, 30, TimeUnit.SECONDS);

        var result = redisTenantService.getObject(key, HashMap.class);*/

        redisTenantService.put("helo", "12", 30, TimeUnit.SECONDS);


        return "result";
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


    public void testKafka1(MeetingMessage request){
        kafkaTenantService.sendMessage(MeetingMessage.builder()
                .message(request.getMessage())
                .senderId(UUID.randomUUID().toString())
                .title(request.getTitle())
                .recipientId(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build(), "create-notification-topic");
    }




}
