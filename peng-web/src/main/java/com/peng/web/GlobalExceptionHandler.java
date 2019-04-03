package com.peng.web;

import com.peng.kit.common.MDCTraceUtil;
import com.peng.web.exception.ErrorResult;
import com.peng.web.exception.ExceptionHandlerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/4/3
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private ExceptionHandlerContainer handlerContainer;

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleException(HttpServletRequest res, HttpServletResponse resp, Throwable e){
        ErrorResult result = handlerContainer.handleException(res,resp,e);
        if (result.getCode() == null ){

        }
        if (!resp.isCommitted()){
            HttpStatus status = result.getStatus();
            if (status==null)
                resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            else
                resp.setStatus(status.value());
        }
        result.setTraceId(MDCTraceUtil.INSTANCE.getTraceId());
        log.warn("request occur error,code: {},message: {}",result.getCode(),result.getMessage());
        return result;
    }
}
