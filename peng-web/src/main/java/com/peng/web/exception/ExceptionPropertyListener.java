package com.peng.web.exception;

import com.peng.kit.common.PropertyUtil;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * created by guoqingpeng on 2019/4/4
 */
public class ExceptionPropertyListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        PropertySource<?> propertySource = applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources().get("applicationConfig: [classpath:/application.yml]");
        if (StringUtils.isEmpty(propertySource))
            return;
        String property = (String)propertySource.getProperty("web.exception.property-path");
        if (StringUtils.isEmpty(property))
            return;
        Map<String,String> map = PropertyUtil.getProperties(property);
        for (String key : map.keySet()){
            ErrorResultRegistry.addCodeMsg(Integer.valueOf(key), map.get(key));
        }
    }
}
