package com.peng.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * created by guoqingpeng on 2019/3/31
 */
@ConfigurationProperties(prefix = "log")
@Data
public class LogProperties {
    //末尾会拼接-%{yyyy-MM-dd}-%i.log
    private String logName;
    //log的输出的根目录
    private String logPath;
    //配置dao层的路径时候，就会打印该层的sql
    private boolean showSql=false;
    //异步打印日志的队列深度
    private String queueSize="256";
    //单个日志文件的大小
    private String fileSize="100MB";
    //保留日志的天数
    private String holdTime="30";
    //日志文件文件总大小限制
    private String totalSizeCap="10GB";
    //单个错误日志文件的大小
    private String errorFileSize="100MB";
    //错误保留日志的天数
    private String errorHoldTime="60";
    //错误日志文件文件总大小限制
    private String errorTotalSizeCap="5GB";
    //log展示的信息
    private String logPattern="%d{yyyy-MM-dd HH:mm:ss:SSS} %X{traceId} %-5level %thread %logger{36} %line %msg%n";
}
