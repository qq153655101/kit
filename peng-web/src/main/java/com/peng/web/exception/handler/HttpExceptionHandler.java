package com.peng.web.exception.handler;

import com.peng.web.exception.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/4/3
 */
public class HttpExceptionHandler implements ExceptionHandler {
    @Override
    public Result handleException(HttpServletRequest res, HttpServletResponse resp, Throwable e, Throwable mostSpecificCause) {
        //todo http异常处理
        return null;
    }
}
