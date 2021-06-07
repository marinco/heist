package com.agency04.heist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolTaskSchedulerConfiguration {

    @Value("${threadPoolTaskScheduler.threadNamePrefix}")
    private String threadNamePrefix;

    @Value("${threadPoolTaskScheduler.maxPoolSize}")
    private int maxPoolSize;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(maxPoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix(threadNamePrefix);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
