package com.peng.web;

import com.peng.common.spring.MetadataReaderProducer;
import com.peng.web.exception.ErrorCodeMsg;
import com.peng.web.exception.ErrorCodeMsgBuliderProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ImportAutoConfiguration(GlobalExceptionHandler.class)
public class WebConfiguretion {

    @Bean
    @ConditionalOnProperty(name = "web.cors.enable",havingValue = "true")
    public FilterRegistrationBean corsFilter(WebProperties webProperties){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        WebProperties.Cors cors = webProperties.getCors();
        if (cors.getAllowedOrigins() == null)
            cors.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**",cors);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setUrlPatterns(Collections.singletonList("/*"));
        bean.setName("corsFilter");
        bean.setOrder(cors.getOrder());
        return bean;
    }

    @Bean
    public static MetadataReaderProducer metadataReaderProducer(){
        return new MetadataReaderProducer();
    }

    @Bean
    @ConditionalOnExpression("'enum'.equals('${web.exception.error-code-source}') || 'both'.equals('${web.exception.error-code-source}') ")
    public static ErrorCodeMsgBuliderProcessor codeMsgBeanDefinitionProcessor(MetadataReaderProducer metadataReaderProducer){
        return new ErrorCodeMsgBuliderProcessor(metadataReaderProducer, ErrorCodeMsg.class);
    }
}
