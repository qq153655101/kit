package com.peng.web.exception;

import com.peng.common.spring.MetadataReaderProducer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;

/**
 * created by guoqingpeng on 2019/4/4
 */
public abstract   class AbstractErrorCodeMsgBeanDefinitionProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, Ordered {

    private final MetadataReaderProducer metadataReaderProducer;

    private final Class<? extends Annotation> c;

    public AbstractErrorCodeMsgBeanDefinitionProcessor(MetadataReaderProducer metadataReaderProducer,Class<? extends Annotation> c){
        this.metadataReaderProducer = metadataReaderProducer;
        this.c = c;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String annoName = c.getName();
        metadataReaderProducer.prcessMetadataReader(metadataReader -> {

        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
