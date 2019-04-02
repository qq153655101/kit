package com.peng.common.spring.interceptor;

import com.peng.kit.common.MDCTraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/3/31
 */
@Slf4j
public class WebLogInterceptor extends HandlerInterceptorAdapter {

    private final String EXECUT_TIME_KEY="execute_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        log.info("-----------------------------start:{}---------------------------------",MDCTraceUtil.INSTANCE.getTraceId());
        request.setAttribute(EXECUT_TIME_KEY,System.currentTimeMillis());
        log.info("request uri:{}"+request.getRequestURI());
        String queryString = request.getQueryString();
        log.info("the queryString is:"+queryString);
        response.setHeader("traceId", MDCTraceUtil.INSTANCE.getTraceId());
        return super.preHandle(request,response,handler);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,Exception e) throws Exception{
        Object attribute = request.getAttribute(EXECUT_TIME_KEY);
        if (attribute != null){
            long start = (long) attribute;
            log.info("execute time:{}",System.currentTimeMillis()-start);
        }
        log.info("-----------------------------end:{}---------------------------------",MDCTraceUtil.INSTANCE.getTraceId());
        super.afterCompletion(request,response,handler,e);
    }

}
