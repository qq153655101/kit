package com.peng.log;

import com.peng.common.spring.processor.AbstractPengEnvPostProcessor;
import com.peng.kit.common.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.swing.*;

/**
 * created by guoqingpeng on 2019/3/31
 */
public class LogEnvSetter extends AbstractPengEnvPostProcessor {

    @Override
    protected void commonProfiles(ConfigurableEnvironment environment, SpringApplication application){}
    @Override
    protected void onLocalEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application,"logback-test.xml");
    }
    @Override
    protected void onTestEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application,"logback-test.xml");
    }

    @Override
    protected void onPrdEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application,"logback-prd.xml");
    }

    private void setLogProperties(ConfigurableEnvironment environment, SpringApplication application,String logbackConfigPath){
        Binder binder = Binder.get(environment);
        BindResult<LogProperties> bindLogProperty = binder.bind("log", Bindable.of(LogProperties.class));
        LogProperties logProperties;
        if (bindLogProperty.isBound())
            logProperties = bindLogProperty.get();
        else
            logProperties = new LogProperties();
        String fileName = logProperties.getLogName()==null?environment.getProperty("spring.application.name"):logProperties.getLogName();
        String rootLogDir = logProperties.getLogPath()==null?"/home/log/":(logProperties.getLogPath()+"/");
        String filePattern = rootLogDir+fileName+"info/info"+"-%d{yyyy-MM-dd}-%i.log";
        String errorFilePattern = rootLogDir+fileName+"error/error"+"-%d{yyyy-MM-dd}-%i.log";
        System.setProperty("DAO_PATH",logProperties.getDaoPath());
        System.setProperty("FILE_SIZE",logProperties.getFileSize());
        System.setProperty("MAX_HISTORY_DAY",logProperties.getHoldTime());
        System.setProperty("TOTAL_CAP_SIZE",logProperties.getTotalSizeCap());
        System.setProperty("ERROR_FILE_SIZE",logProperties.getErrorFileSize());
        System.setProperty("ERROR_MAX_HISTORY_DAY",logProperties.getErrorHoldTime());
        System.setProperty("ERROR_TOTAL_CAP_SIZE",logProperties.getErrorTotalSizeCap());
        System.setProperty("QUEUE_SIEZ",logProperties.getQueueSize());
        System.setProperty("OUTPUT_STYLE",logProperties.getLogPattern());
        System.setProperty("FILE_PATTERN",filePattern);
        System.setProperty("ERROR_FILE_PATTERN",errorFilePattern);
        SpringUtils.setEnvproperty(environment,"logging.config","classpath:"+logbackConfigPath);
    }
}
