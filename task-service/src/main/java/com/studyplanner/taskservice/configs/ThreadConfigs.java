package com.studyplanner.taskservice.configs;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfigs {
    static {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}