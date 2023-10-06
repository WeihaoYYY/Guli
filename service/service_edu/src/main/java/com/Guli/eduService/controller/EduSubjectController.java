package com.Guli.eduService.controller;


import com.Guli.eduService.entity.subject.OneSubject;
import com.Guli.eduService.service.EduSubjectService;
import commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Weihao
 * @since 2023-08-27
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
@Api(description = "课程分类上传")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传的文件，把文件内容读取出来
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
            subjectService.saveSubject(file, subjectService);

            return R.ok();
    }

    @ApiOperation(value = "课程分类列表（树形）")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }


}

