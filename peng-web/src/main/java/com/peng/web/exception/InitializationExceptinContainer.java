package com.peng.web.exception;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 使用ErrorCodeMsgBeanDefinitionProcessor初始化异常消息容器
 * created by guoqingpeng on 2019/4/3
 */
@Deprecated
public class InitializationExceptinContainer implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //ErrorResultRegistry.addCodeMsg(110,"");
    }
}
