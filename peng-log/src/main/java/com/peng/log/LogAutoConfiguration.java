package com.peng.log;

import com.peng.common.spring.interceptor.WebHeaderInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.sleuth.instrument.web.TraceWebAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * created by guoqingpeng on 2019/3/31
 */
@AutoConfigureAfter(TraceWebAutoConfiguration.class)
@Configuration
public class LogAutoConfiguration {

    @Configuration
    public static class WebLogMvcConfigurer implements WebMvcConfigurer{
        @Override
        public void addInterceptors(InterceptorRegistry registration){
            registration.addInterceptor(new WebHeaderInterceptor());
        }
    }
}
