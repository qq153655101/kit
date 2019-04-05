package com.peng.web.exception.carrier;

import org.springframework.http.HttpStatus;

/**
 * created by guoqingpeng on 2019/4/5
 */
public class VerifyException extends CommonException {

    public VerifyException(int code, String messsage) {
        super(code, messsage);
    }

    public VerifyException(String message, int code, String[] arguments, HttpStatus status) {
        super(message, code, arguments, status);
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(int code) {
        super(code);
    }

    public VerifyException(Throwable cause) {
        super(cause);
    }

    public VerifyException(int code, String[] arguments) {
        super(code, arguments);
    }

    public VerifyException(Throwable cause, int code, String[] arguments) {
        super(cause, code, arguments);
    }

    public VerifyException(String message, Throwable cause, int code, String[] arguments) {
        super(message, cause, code, arguments);
    }
}
