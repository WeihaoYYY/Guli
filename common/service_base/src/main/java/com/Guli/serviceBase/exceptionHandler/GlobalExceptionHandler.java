package com.Guli.serviceBase.exceptionHandler;


import commonUtils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice  //@ControllerAdvice是捕获异常的 相当于在整个Controller层套了一个trycatch
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)  ///全局异常
    @ResponseBody  //为了返回数据
    public R globalError(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("执行了全局异常处理..");
    }

    @ExceptionHandler(IOException.class)  ///特定异常
    @ResponseBody  //为了返回数据
    public R IOError(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局IO异常处理..");
    }

    @ExceptionHandler(GuliException.class)  ///自定义异常
    @ResponseBody  //为了返回数据
    public R GuliError(GuliException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }





}