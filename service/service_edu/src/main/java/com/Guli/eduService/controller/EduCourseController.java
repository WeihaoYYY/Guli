package com.Guli.eduService.controller;


import com.Guli.eduService.entity.VO.CourseInfoVo;
import com.Guli.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import commonUtils.R;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {  //RequestBody将表单数据封装到courseInfoVo对象中
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

}

