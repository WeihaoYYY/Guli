package com.Guli.eduService.controller;

import commonUtils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class EduLoginController {

    //login
    @PostMapping("login")
    public R login(){

        return R.ok().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://img2.baidu.com/it/u=14823864,430440209&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=334");
    }



}
