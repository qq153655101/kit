package com.peng.web.exception;

import com.peng.common.spring.MetadataReaderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 扫描注解找到有ErrorCodeMsg注解的enum，根据注解和enum初始化异常消息容器
 * created by guoqingpeng on 2019/4/4
 */
@Slf4j
public class ErrorCodeMsgBuliderProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, Ordered {

    private final MetadataReaderProducer metadataReaderProducer;

    private final Class<? extends Annotation> c;

    public ErrorCodeMsgBuliderProcessor(MetadataReaderProducer metadataReaderProducer, Class<? extends Annotation> c){
        this.metadataReaderProducer = metadataReaderProducer;
        this.c = c;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String enumClassNmae = environment.getProperty("web.exception.enum-class-name");
        if (enumClassNmae == null )
            return ;
        String annoName = c.getName();
        metadataReaderProducer.prcessMetadataReader(metadataReader -> {
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(annoName);
            if (CollectionUtils.isEmpty(annotationAttributes))
                return ;
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            if (!classMetadata.isFinal() || !"java.lang.Enum".equals(classMetadata.getSuperClassName())
                    || !classMetadata.getClassName().equals(enumClassNmae))
                return ;
            String getCode = (String)annotationAttributes.get("getCodeMethod");
            String getMsg = (String)annotationAttributes.get("getMsgMethod");
            setErrorCodeMsgByEnum(classMetadata.getClassName(),getCode,getMsg);
        });

    }

    private void setErrorCodeMsgByEnum(String classNmae,String getCodeMethod,String getMsgMethod){
        try {
            Class<Enum> c = (Class<Enum>) ClassUtils.forName(classNmae,ClassUtils.getDefaultClassLoader());
            Method codeMethod = c.getMethod(getCodeMethod);
            Method msgMethod = c.getMethod(getMsgMethod);
            Enum[] enumConstants = c.getEnumConstants();
            for (Enum e:enumConstants){
                ErrorResultRegistry.addCodeMsg((Integer) codeMethod.invoke(e),(String) msgMethod.invoke(e));
            }
        }catch (Exception e){
            log.error("ErrorCodeMsgBuliderProcessor occur error:{}",e);
        }
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
