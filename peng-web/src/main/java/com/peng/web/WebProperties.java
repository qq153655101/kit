package com.peng.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;

/**
 * created by guoqingpeng on 2019/4/3
 */
@ConfigurationProperties(prefix = "web")
public class WebProperties {

    private Exception exception = new Exception();

    private Cors cors = new Cors();


    public class Exception{
        private String errorCodeSource;//properties,enum,both
        private String propertyPath;//classpath:exception.properties
        private String enumClassName;//

        public String getErrorCodeSource() {
            return errorCodeSource;
        }

        public void setErrorCodeSource(String errorCodeSource) {
            this.errorCodeSource = errorCodeSource;
        }

        public String getPropertyPath() {
            return propertyPath;
        }

        public void setPropertyPath(String propertyPath) {
            this.propertyPath = propertyPath;
        }

        public String getEnumClassName() {
            return enumClassName;
        }

        public void setEnumClassName(String enumClassName) {
            this.enumClassName = enumClassName;
        }
    }

    public class Cors extends CorsConfiguration {
        private boolean enable;
        private int order = Ordered.HIGHEST_PRECEDENCE;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Cors getCors() {
        return cors;
    }

    public void setCors(Cors cors) {
        this.cors = cors;
    }
}
