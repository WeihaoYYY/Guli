package com.Guli.eduService.controller;


import com.Guli.eduService.entity.EduCourse;
import com.Guli.eduService.entity.VO.CourseInfoVo;
import com.Guli.eduService.entity.VO.CoursePublishVo;
import com.Guli.eduService.entity.VO.CourseQuery;
import com.Guli.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import commonUtils.R;

import java.util.List;

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

    @ApiOperation(value = "根据课程查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation(value = "根据id修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @ApiOperation(value = "课程最终发布修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation(value = "课程列表")
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }


    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }






}

