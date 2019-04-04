package com.peng.web.exception;

import com.peng.kit.common.PropertyUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.Map;

/**
 * created by guoqingpeng on 2019/4/4
 */
public class ExceptionPropertyListener implements ApplicationListener<ApplicationStartedEvent> {
    public static Map<String,String> map = new HashMap<>();
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        map = PropertyUtil.getProperties("exception.properties");
    }
}
