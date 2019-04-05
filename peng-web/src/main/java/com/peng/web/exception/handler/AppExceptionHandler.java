package com.peng.web.exception.handler;

import com.peng.web.exception.ErrorResultRegistry;
import com.peng.web.exception.Result;
import com.peng.web.exception.carrier.CommonException;
import org.springframework.core.PriorityOrdered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by guoqingpeng on 2019/4/3
 */
public class AppExceptionHandler implements ExceptionHandler , PriorityOrdered {
    @Override
    public Result handleException(HttpServletRequest res, HttpServletResponse resp, Throwable e, Throwable mostSpecificCause) {
        Result result;
        if (e instanceof CommonException || mostSpecificCause instanceof CommonException){
            CommonException exception = (CommonException) e;
            int code = exception.getCode();
            String[] args = exception.getArguments();
            if (exception.getStatus() != null){
                result = Result.builder().code(code).message(exception.getMessage()).status(exception.getStatus()).build();
            }else {
                result = Result.builder().code(code).message(ErrorResultRegistry.getMessageByCode(code,args)).status(exception.getStatus()).build();
            }
        }else {
            result = Result.builder().code(0).message("").build();
        }
        return result;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
