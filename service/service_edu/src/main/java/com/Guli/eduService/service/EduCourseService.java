package com.Guli.eduService.service;

import com.Guli.eduService.entity.EduCourse;
import com.Guli.eduService.entity.VO.CourseInfoVo;
import com.Guli.eduService.entity.VO.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);
}
