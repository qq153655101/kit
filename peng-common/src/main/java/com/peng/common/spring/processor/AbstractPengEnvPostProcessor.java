package com.peng.common.spring.processor;

import com.peng.kit.common.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * created by guoqingpeng on 2019/3/31
 */
public abstract class AbstractPengEnvPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (SpringUtils.isSpringBootApp(application)){
            String[] activeProfiles = environment.getActiveProfiles();
            String profile = "local";
            if (activeProfiles.length>0)
                profile = activeProfiles[0];
            switch (profile){
                case "test":
                    onTestEnv(environment,application);
                    break;
                case "prd":
                    onPrdEnv(environment,application);
                    break;
                case "local":
                default:
                    onLocalEnv(environment,application);
                    break;
            }
            commonProfiles(environment,application);
        }
    }

    protected void commonProfiles(ConfigurableEnvironment environment, SpringApplication application){}
    protected void onLocalEnv(ConfigurableEnvironment environment, SpringApplication application){}
    protected void onTestEnv(ConfigurableEnvironment environment, SpringApplication application){}
    protected void onPrdEnv(ConfigurableEnvironment environment, SpringApplication application){}

}
