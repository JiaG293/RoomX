package com.roomx.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.roomx") // Quét tất cả trong package để tìm bean
@EntityScan(basePackages = "com.roomx.infrastructure") // Để lấy được các entity mapping với database
@ConfigurationPropertiesScan(basePackages = "com.roomx")
@EnableJpaRepositories(basePackages = "com.roomx.infrastructure")
@EnableCaching
//@EnableTransactionManagement
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
