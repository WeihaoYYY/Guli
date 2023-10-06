package com.Guli.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.Guli"})  //扫描SwaggerConfig类 ，不添加这个的话默认只扫描当前模块下的类
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
        System.out.println("Hello World!");
    }
}
