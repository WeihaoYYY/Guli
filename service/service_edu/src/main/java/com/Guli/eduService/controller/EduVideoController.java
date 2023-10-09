package com.Guli.eduService.controller;


import com.Guli.eduService.entity.EduVideo;
import com.Guli.eduService.service.EduVideoService;
import commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
@Api(description = "小节")
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation(value = "根据小节id查询")
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //TODO 后面这个方法需要完善：删除小节时候，同时要把里面的视频删除
    @ApiOperation(value = "删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.removeById(id);
        return R.ok();
    }
}


