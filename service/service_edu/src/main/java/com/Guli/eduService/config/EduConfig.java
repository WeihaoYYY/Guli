package com.Guli.eduService.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@MapperScan("com.Guli.eduService.mapper")
public class EduConfig {

    @Bean  //逻辑删除插件
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

    @Bean //分页插件
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
