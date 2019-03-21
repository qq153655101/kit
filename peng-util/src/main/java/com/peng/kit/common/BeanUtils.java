package com.peng.kit.common;

import lombok.SneakyThrows;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by guoqingpeng on 2019/3/20
 */

public class BeanUtils {

    @SneakyThrows
    public static <T> T copyBean(Object object,Class<T> tClass){
        BeanCopier copier = BeanCopier.create(object.getClass(),tClass,false);
        T t = tClass.newInstance();
        copier.copy(object,t,null);
        return t;
    }

    @SneakyThrows
    public static <T> List<T> copyListBean(List<?> list,Class<T> tClass){
        if (CollectionUtils.isEmpty(list))
            return null;
        return list.stream().map(c->copyBean(c,tClass)).collect(Collectors.toList());
    }

    @SneakyThrows
    public static Map<String,Object> convertObjectToMap(Object object){
        Map<String,Object> map = new HashMap<>();
        processProperties(object,(s,o)->{
            if (o==null)
                map.put(s,"");
            else
                map.put(s,o);
        });
        return map;
    }

    @FunctionalInterface
    public interface PropertyProcessor<P,V>{
        void process(P propertyNmae, V val);
    }

    @SneakyThrows
    public static void processProperties(Object object,PropertyProcessor<String ,Object>  processor){
        Class type = object.getClass();
        PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(type);
        for (PropertyDescriptor descriptor:propertyDescriptors){
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)){
                Method readMethod = descriptor.getReadMethod();
                if (null != readMethod){
                    Object result = readMethod.invoke(object);
                    processor.process(propertyName,result);
                }
            }
        }
    }


}
