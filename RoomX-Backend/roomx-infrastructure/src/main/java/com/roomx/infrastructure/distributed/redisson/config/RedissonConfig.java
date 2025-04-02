package com.roomx.infrastructure.distributed.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisUrl = "redis://" + host + ":" + port;
        config.useSingleServer()
                .setAddress(redisUrl)
                .setUsername(username.isEmpty() ? null : username)
                .setPassword(password.isEmpty() ? null : password)
                .setConnectionPoolSize(50)
                .setDatabase(0);
        return Redisson.create(config);
    }

}

