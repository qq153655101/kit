package com.peng.kit.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
}
