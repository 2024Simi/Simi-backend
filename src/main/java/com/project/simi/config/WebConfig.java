package com.project.simi.config;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.simi.filter.ApiLoggingFilter;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ApiLoggingFilter apiLoggingFilter;

    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> loggingFilter() {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean =
                new FilterRegistrationBean<>(apiLoggingFilter);
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
