package com.peng.common.spring;

import com.peng.kit.common.SpringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * created by guoqingpeng on 2019/4/4
 */
public class MetadataReaderProducer implements ApplicationListener<ContextRefreshedEvent> , ApplicationContextAware {

    private CachingMetadataReaderFactory metadataReaderFactory;

    private ResourcePatternResolver resourcePatternResolver;

    private ApplicationContext applicationContext;

    private volatile  boolean cleared = false;

    @SneakyThrows
    public void prcessMetadataReader(Consumer<MetadataReader> consumer){
        List<String> basePackages = SpringUtils.basePackage;
        List<Resource> resources = getResourceFromBasePackage(basePackages);
        HashSet checkRepeat = new HashSet();
        for (Resource resource:resources){
            String uriString = resource.getURI().toString();
            if (!checkRepeat.add(uriString))
                continue;;
            if (resource.isReadable()) {
                 MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                 consumer.accept(metadataReader);
            }
        }
    }

    public List<Resource> getResourceFromBasePackage(List<String> basePackages) throws IOException {
        List<Resource> resources = new ArrayList<>();
        for (String basePackage : basePackages){
            String packagePath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage))
                    +"/**/*.class";
            resources.addAll(Arrays.asList(resourcePatternResolver.getResources(packagePath)));
        }
        return resources;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.metadataReaderFactory = new CachingMetadataReaderFactory(applicationContext);
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(applicationContext);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (metadataReaderFactory != null && !cleared && contextRefreshedEvent.getApplicationContext() == this.applicationContext ){
            metadataReaderFactory.clearCache();
            cleared = true;
        }
    }
}
