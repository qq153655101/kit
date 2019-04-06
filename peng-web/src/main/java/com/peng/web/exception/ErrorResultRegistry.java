package com.peng.web.exception;

import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * created by guoqingpeng on 2019/4/3
 */
public class ErrorResultRegistry {

    public static final ConcurrentHashMap<Integer,String> REGISTRY = new ConcurrentHashMap<>();

    public static void addCodeMsg(Integer code,String msg){
        REGISTRY.put(code,msg);
    }

    public static String getMessageByCode(Integer code){
        return REGISTRY.get(code);
    }

    public static String getMessageByCode(Integer code,String ...args){
        if (ObjectUtils.isEmpty(args))
            return REGISTRY.get(code);
        return String.format(REGISTRY.get(code),(Object[]) args);
    }
}
