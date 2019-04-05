package com.peng.common.spring.listener;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * created by guoqingpeng on 2019/4/4
 */
@Getter
public class SpringApplicationRunListenerAdapter implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;

    //SpringApplicationRunListener的实现者必须有此构造方法，SpringApplicationRunListeners会自动注入一下两个参数
    public SpringApplicationRunListenerAdapter(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
