package com.peng.web.exception.handler;

import com.peng.web.exception.ErrorResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/4/3
 */
public interface ExceptionHandler {
    ErrorResult handleException(HttpServletRequest res, HttpServletResponse resp,Throwable e,Throwable mostSpecificCause);
}
