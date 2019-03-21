package com.peng.kit.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * created by guoqingpeng on 2019/3/21
 */
@Slf4j
public class JsonUtils {

    /**
     * jsonObj字符串类型或者JSONObject类型
     */
    public static  <T> T parser(Object jsonObj,Class<T> c){
        if (StringUtils.isEmpty(jsonObj))
            return null;
        return JSONObject.parseObject(jsonObj.toString(),c);
    }

    public static <T> List<T> parerToList(String jsonStr,Class<T> tClass){
        if (StringUtils.isEmpty(jsonStr))
            return null;
        return JSON.parseArray(jsonStr,tClass);
    }

    public static JSONObject toFJson(Object o){
        if (o == null)
            return null;
        return JSONObject.parseObject(JSON.toJSONString(o));
    }

    public static String toJkson(Object o){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
           return objectMapper.writeValueAsString(o);
        }catch (Exception e){
            throw new RuntimeException("jackson parser error !");
        }
    }

    public static <T> T parserByJack(String json,Class<T> c){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readerFor(c).readValue(json);
        }catch (Exception e){
            throw new RuntimeException("jackson parser error !");
        }
    }

}
