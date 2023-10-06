package com.Guli.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//InitializingBean接口是在bean初始化之后执行，用于初始化其他参数
public class ConstandPropertiesUtils implements InitializingBean {

    //读取配置文件application.properties内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.accessKeyId}")
    private String keyid;

    @Value("${aliyun.oss.file.accessKeySecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketName}")
    private String bucketname;


    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    @Override  //当项目已启动，spring接口，spring加载之后，执行接口一个方法
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}
