package com.peng.log;

import com.peng.common.spring.processor.AbstractPengEnvPostProcessor;
import com.peng.kit.common.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * created by guoqingpeng on 2019/3/31
 */
public class LogEnvSetter extends AbstractPengEnvPostProcessor {

    @Override
    protected void commonProfiles(ConfigurableEnvironment environment, SpringApplication application){}
    @Override
    protected void onLocalEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application, "logback-peng.xml");
    }
    @Override
    protected void onTestEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application, "logback-peng.xml");
    }

    @Override
    protected void onPrdEnv(ConfigurableEnvironment environment, SpringApplication application){
        setLogProperties(environment,application, "logback-peng.xml");
    }

    private void setLogProperties(ConfigurableEnvironment environment, SpringApplication application,String logbackConfigPath){
        Binder binder = Binder.get(environment);
        BindResult<LogProperties> bindLogProperty = binder.bind("peng.properties.log", Bindable.of(LogProperties.class));
        LogProperties logProperties;
        if (bindLogProperty.isBound())
            logProperties = bindLogProperty.get();
        else{
            logProperties = new LogProperties();
            SpringUtils.setEnvproperty(environment,"log.logPattern",logProperties.getLogPattern());
            SpringUtils.setEnvproperty(environment,"log.holdTime",logProperties.getHoldTime());
            SpringUtils.setEnvproperty(environment,"log.fileSize",logProperties.getFileSize());
            SpringUtils.setEnvproperty(environment,"log.totalSizeCap",logProperties.getTotalSizeCap());
            SpringUtils.setEnvproperty(environment,"log.errorFileSize",logProperties.getErrorFileSize());
            SpringUtils.setEnvproperty(environment,"log.errorHoldTime",logProperties.getErrorHoldTime());
            SpringUtils.setEnvproperty(environment,"log.errorTotalSizeCap",logProperties.getErrorTotalSizeCap());
            SpringUtils.setEnvproperty(environment,"log.queueSize",logProperties.getQueueSize());
            SpringUtils.setEnvproperty(environment,"log.showSql",logProperties.isShowSql());
        }
        String fileName = logProperties.getLogName()==null?environment.getProperty("spring.application.name"):logProperties.getLogName();
        String rootLogDir = logProperties.getLogPath()==null?"/myspace/log/":(logProperties.getLogPath()+"/");
        String filePattern = rootLogDir+fileName+"/info/info"+"-%d{yyyy-MM-dd}-%i.log";
        String errorFilePattern = rootLogDir+fileName+"/error/error"+"-%d{yyyy-MM-dd}-%i.log";
        SpringUtils.setEnvproperty(environment,"log.filePattern",filePattern);
        SpringUtils.setEnvproperty(environment,"log.errorFilePattern",errorFilePattern);
        SpringUtils.setEnvproperty(environment,"logging.config","classpath:"+logbackConfigPath);
    }
}
