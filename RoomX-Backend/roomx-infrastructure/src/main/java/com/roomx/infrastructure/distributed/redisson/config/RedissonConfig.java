/*
package com.roomx.infrastructure.distributed.redisson.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

    @Value("${spring.data.redis.username}")
    private String username;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.connect-timeout}")
    private int connectionTimeOut;

    @Value("${spring.data.redis.redisson.pool.max-active}")
    private int maxActive;

    @Value("${spring.data.redis.redisson.pool.min-idle}")
    private int minIdle;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisUrl = "redis://" + host + ":" + port;
        var server = config.useSingleServer()
                .setAddress(redisUrl)
                .setUsername(username.isEmpty() ? null : username)
                .setPassword(password.isEmpty() ? null : password)
                .setConnectionPoolSize(maxActive)
                .setConnectionMinimumIdleSize(minIdle)
                .setIdleConnectionTimeout(connectionTimeOut)
                .setDatabase(0);

        log.info("address info redis: {}", server.getAddress());
        return Redisson.create(config);
    }





}

*/
