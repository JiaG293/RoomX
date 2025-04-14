package com.roomx.infrastructure.quartz.config;


import lombok.RequiredArgsConstructor;
import org.quartz.spi.JobFactory;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    private final QuartzProperties quartzProperties;

    @Bean
    public JobFactory jobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean quartzScheduler(JobFactory jobFactory) {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setJobFactory(jobFactory);

        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        quartzScheduler.setQuartzProperties(properties);

        quartzScheduler.setApplicationContextSchedulerContextKey("applicationContext");
        return quartzScheduler;
    }


}
