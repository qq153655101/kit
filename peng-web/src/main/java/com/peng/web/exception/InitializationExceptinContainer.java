package com.peng.web.exception;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * created by guoqingpeng on 2019/4/3
 */
public class InitializationExceptinContainer implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //ErrorResultRegistry.addCodeMsg(110,"");
    }
}
