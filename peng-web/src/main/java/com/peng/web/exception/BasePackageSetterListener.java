package com.peng.web.exception;

import com.peng.common.spring.listener.SpringApplicationRunListenerAdapter;
import com.peng.kit.common.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * created by guoqingpeng on 2019/4/5
 */
public class BasePackageSetterListener extends SpringApplicationRunListenerAdapter {

    public BasePackageSetterListener(SpringApplication application, String[] args) {
        super(application, args);
    }

    @Override
    public void starting() {
        super.starting();
        if (SpringUtils.isSpringBootApp(getApplication())){
            Class<?> mainApplicationClass = getApplication().getMainApplicationClass();
            ComponentScan annotation = mainApplicationClass.getAnnotation(ComponentScan.class);
            Set<String> packages = new HashSet<>();
            packages.add(mainApplicationClass.getPackage().getName());
            if (annotation != null){
                if (annotation.value().length != 0){
                    for (String value : annotation.value()){
                        if (StringUtils.hasText(value))
                            packages.add(value);
                    }
                }
                if (annotation.basePackages().length != 0){
                    for (String value : annotation.basePackages()){
                        if (StringUtils.hasText(value))
                            packages.add(value);
                    }
                }
                if (annotation.basePackageClasses().length != 0){
                    for (Class<?> value : annotation.basePackageClasses()){
                        packages.add(value.getPackage().getName());
                    }
                }
                SpringUtils.setBasePackage(new LinkedList<>(packages));
            }
        }
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        super.environmentPrepared(environment);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        super.contextPrepared(context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        super.contextLoaded(context);
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        super.started(context);
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        super.running(context);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        super.failed(context, exception);
    }
}
