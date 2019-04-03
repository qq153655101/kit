package com.peng.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;

/**
 * created by guoqingpeng on 2019/4/3
 */
@ConfigurationProperties(prefix = "web")
@Data
public class WebProperties {

    private Exception exception;

    private Cors cors;

    @Data
    public static class Exception{
        private String errorCodeSource;//properties,enum,both
    }

    @Data
    public static class Cors extends CorsConfiguration {
        private boolean enable;
        private int order = Ordered.HIGHEST_PRECEDENCE;
    }
}
