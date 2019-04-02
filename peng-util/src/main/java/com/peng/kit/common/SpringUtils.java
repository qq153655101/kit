package com.peng.kit.common;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * created by guoqingpeng on 2019/3/20
 */
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> classType){
        return applicationContext.getBean(classType);
    }

    public static <T> T getBean(String beanName){
        return (T)applicationContext.getBean(beanName);
    }

    public static String getActiveProfile(){
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    public static String getAppName(){
        return applicationContext.getEnvironment().getProperty("spring.application.name");
    }

    public static boolean isSpringBootApp(SpringApplication application){
        if (application == null)
            return false;
        Set<Object> sources = application.getAllSources();
        if (sources.size()>1)
            return false;
        return sources.contains(application.getMainApplicationClass());
    }

    public static void setEnvproperty(ConfigurableEnvironment environment,String key,Object value){
        putEnvProperty(environment,key,value,false);
    }

    public static void setEnvpropertyIfAbsent(ConfigurableEnvironment environment,String key,Object value){
        putEnvProperty(environment,key,value,true);
    }

    private static void putEnvProperty(ConfigurableEnvironment environment,String key,Object value,boolean check){
        Map<String, Object> source = getEnvPropretySource(environment);
        if (check){
            Object old = source.get(key);
            //todo 可能存在脏读，暂时忽略
            if (old == null)
                source.put(key,value);
        }else
            source.put(key,value);
    }

    private static Map<String,Object> getEnvPropretySource(ConfigurableEnvironment environment){
        MutablePropertySources propertySources = environment.getPropertySources();
        MapPropertySource mapPropertySource = (MapPropertySource)propertySources.get("peng.properties");
        Map<String,Object> source;
        if(mapPropertySource == null){
            source = new HashMap<>();
            propertySources.addLast(new MapPropertySource("peng.properties",source));
        }else{
            source =  mapPropertySource.getSource();
        }
        return source;
    }
}
