package com.Guli.eduService.controller;


import com.Guli.eduService.entity.chapter.ChapterVo;
import com.Guli.eduService.service.EduChapterService;
import commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduService/chapter")
@Api(description = "章节")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //根据课程id进行查询
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }


}

