package com.peng.web.exception;

import java.lang.annotation.*;

/**
 * created by guoqingpeng on 2019/4/4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorCodeMsg {

    String name();

    String type() default "properties"; //properties,enum,both

    String getCodeMethod() default "getCode";

    String getMsgMethod() default "getMsg";

    String includePropertySource() default "classpath:exception.properties";

}
