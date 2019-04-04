package com.peng.kit.common;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * created by guoqingpeng on 2019/4/4
 */
public class PropertyUtil {

    public static Map<String,String> getProperties(String fileName){
        try {
            Map<String,String> map = new HashMap<>();
            Properties properties = PropertiesLoaderUtils.loadAllProperties(fileName);
            for (Object key: properties.keySet()){
                map.put(key.toString(), new String(properties.getProperty(key.toString()).getBytes("ISO-8859-1"), "utf-8"));
            }
            return map;
        }catch (Exception e){
            return null;
        }
    }

}
