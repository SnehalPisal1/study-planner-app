package com.studyplanner.taskservice.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Executor;

//@Configuration
//@EnableAsync
public class ExecutorConfigs {
    public static Logger logger = LoggerFactory.getLogger(ExecutorConfigs.class);

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("TaskThread-");

        // Add thread pool monitoring
        executor.setRejectedExecutionHandler((r, exe) -> {
            logger.info("Task Rejected - Pool Saturated!");
            logger.info("Active Threads: " + exe.getActiveCount());
            logger.info("Queued Tasks: " + executor.getQueueSize());
        });

        executor.setTaskDecorator(securityContextTaskDecorator());

        executor.initialize();
        return executor;
    }


    @Bean
    public TaskDecorator securityContextTaskDecorator() {
        return runnable -> {
            SecurityContext contextBeforeExecution = SecurityContextHolder.getContext();
            Authentication auth = contextBeforeExecution.getAuthentication();

            return () -> {
                try {
                    SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                    newContext.setAuthentication(auth);
                    SecurityContextHolder.setContext(newContext);

                    logger.info("SecurityContext successfully set in async thread: {}", Thread.currentThread().getName());

                    runnable.run();
                } finally {
                    SecurityContextHolder.clearContext();
                    logger.info("SecurityContext cleared in async thread: {}", Thread.currentThread().getName());
                }
            };
        };
    }

}