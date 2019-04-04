package com.peng.web.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * created by guoqingpeng on 2019/4/3
 */
@Data
@Builder
public class Result<T> {
    private HttpStatus status;
    private Integer code;
    private String message;
    private T data;
    private String traceId;
}
