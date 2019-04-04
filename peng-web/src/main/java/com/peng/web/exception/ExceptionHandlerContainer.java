package com.peng.web.exception;

import com.peng.web.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * created by guoqingpeng on 2019/4/3
 */
@Slf4j
public class ExceptionHandlerContainer {

    @Autowired
    private List<ExceptionHandler> handlers;

    @PostConstruct
    public void  initSortExceptionHadndler(){
        AnnotationAwareOrderComparator.sort(handlers);
    }
    public Result handleException(HttpServletRequest res, HttpServletResponse resp, Throwable e){
        Result result = null;
        Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(e);
        for (ExceptionHandler handler:handlers) {
            Result errorResult = handler.handleException(res, resp, e, mostSpecificCause);
            if (errorResult != null){
                result = errorResult;
                break;
            }
        }
        if (result ==null)
            result = Result.builder().message("").build();
        return result;
    }
}
