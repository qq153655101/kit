package com.peng.web.exception;

import com.peng.web.exception.carrier.VerifyException;
import org.springframework.util.ObjectUtils;

/**
 * 不能放在util包，防止相互依赖
 * created by guoqingpeng on 2019/4/5
 */
public class VerifyUtil {

    public static void checkArguments(boolean expression,int code){
        if (!expression)
            throw new VerifyException(code);

    }

    public static void checkArguments(boolean expression,int code,String... message){
        if (!expression)
            throw new VerifyException(code,message);

    }

    public static <T> T checkNotNull(T reference,int code){
        if (ObjectUtils.isEmpty(reference))
            throw new VerifyException(code);
        else
            return reference;
    }

    public static <T> T checkNotNull(T reference,int code,String ... arguments){
        if (ObjectUtils.isEmpty(reference))
            throw new VerifyException(code,arguments);
        else
            return reference;
    }

    //不对外提供构造方法
    private VerifyUtil() {
    }
}
