package com.peng.kit.common;

import org.slf4j.MDC;

/**
 * created by guoqingpeng on 2019/3/31
 */
public enum  MDCTraceUtil {

    INSTANCE;

    public String getTraceId(){
        String traceId = MDC.get("traceId");
        return traceId != null && traceId.length() >0 ? traceId : null;
    }
}
