package com.peng.kit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 重试小工具
 * 注意：只能穿行重试
 * created by guoqingpeng on 2019/3/20
 */
@Slf4j
public class Retrier<T> {
    private int retryCount = 3;
    private Predicate<T> predicate;
    private Supplier<T> supplier;
    private T result;

    public Retrier exexute(){
        for (int i=0; i<retryCount; i++){
            try {
                this.result = supplier.get();
            }catch (Exception e){
                log.error("Retrier retry error !");
                continue;
            }
            if (predicate == null){
                break;
            }else if (predicate.test(result)){
                break;
            } else {
                return null;
            }

        }
        return this;
    }

    public Retrier(Object params,String executeClass,String executeMathod,String notNullField){
        this(
                ()->{
                    try {
                        Class<?> c = Class.forName(executeClass);
                        Method m = c.getMethod(executeMathod,params.getClass());
                        m.setAccessible(true);
                        return (T)m.invoke(SpringUtils.getBean(c),params);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }

                },
                s->{
                    if (s == null)
                        return false;
                    Map<String,Object> map = BeanUtils.convertClassToMap(s);
                    return !StringUtils.isEmpty(map) && map.size()>0 &&(StringUtils.isEmpty(notNullField) || !StringUtils.isEmpty(map.get(notNullField)));
                }
        );
    }

    public Retrier(Supplier<T> supplier , Predicate<T> predicate) {
        this.supplier = supplier;
        this.predicate = predicate;
    }

    public Retrier setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public T getResult() {
        return result;
    }
}
