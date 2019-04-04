package com.peng.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.function.Consumer;

/**
 * created by guoqingpeng on 2019/4/4
 */
public class MetadataReaderProducer implements ApplicationListener<ContextRefreshedEvent> , ApplicationContextAware {

    public void prcessMetadataReader(Consumer<MetadataReader> consumer){

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
