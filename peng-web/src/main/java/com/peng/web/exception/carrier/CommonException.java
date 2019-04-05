package com.peng.web.exception.carrier;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * created by guoqingpeng on 2019/4/5
 */
public class CommonException extends RuntimeException {
    @Getter
    private int code;
    @Getter
    private String[] arguments;
    @Getter
    private HttpStatus status;

    public CommonException(int code, String messsage) {
        super(messsage);
        this.arguments = new String[]{messsage};
        this.code = code;
    }

    public CommonException(String message, int code, String[] arguments, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public CommonException(String message) {
        super(message);
    }
    public CommonException(int code) {
        this.code = code;
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(int code, String... arguments) {
        this.code = code;
        this.arguments = arguments;
    }

    public CommonException(Throwable cause, int code, String[] arguments) {
        super(cause);
        this.code = code;
        this.arguments = arguments;
    }

    public CommonException(String message, Throwable cause, int code, String[] arguments) {
        super(message, cause);
        this.code = code;
        this.arguments = arguments;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
