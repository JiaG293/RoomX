package com.roomx.infrastructure.distributed.kafka.config;


import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.infrastructure.tenantdetails.PropertiesTenantDetailsService;
import com.roomx.infrastructure.tenantdetails.TenantDetails;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTenantService{

    private final KafkaTemplate<String, Object> kafkaTemplate;

   private String getTenant(){
       return TenantContextHolder.getRequiredTenantIdentifier();
   }

    public void sendMessage(Object payload, String topic) {
        kafkaTemplate.send(
                MessageBuilder.withPayload(payload)
                        .setHeader(KafkaHeaders.TOPIC, topic)
                        .setHeader("tenant-id", getTenant())
                        .build()
        );
    }
}
