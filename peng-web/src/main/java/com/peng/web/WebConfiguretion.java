package com.peng.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;


/**
 * created by guoqingpeng on 2019/4/3
 */
@Configuration
@EnableConfigurationProperties(WebProperties.class)
public class WebConfiguretion {

    @Bean
    @ConditionalOnProperty(name = "web.cors.enable",havingValue = "true")
    public FilterRegistrationBean corsFilter(WebProperties webProperties){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        WebProperties.Cors cors = webProperties.getCors();
        if (cors.getAllowedOrigins()==null || cors.getAllowedHeaders()==null)
            cors.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**",cors);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setUrlPatterns(Collections.singletonList("/*"));
        bean.setName("corsFilter");
        bean.setOrder(cors.getOrder());
        return bean;
    }
}
