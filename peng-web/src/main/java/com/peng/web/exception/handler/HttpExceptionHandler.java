package com.peng.web.exception.handler;

import com.peng.web.exception.ErrorResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/4/3
 */
public class HttpExceptionHandler implements ExceptionHandler {
    @Override
    public ErrorResult handleException(HttpServletRequest res, HttpServletResponse resp, Throwable e,Throwable mostSpecificCause) {
        return null;
    }
}
