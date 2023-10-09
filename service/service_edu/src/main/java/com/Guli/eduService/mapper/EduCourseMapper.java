package com.Guli.eduService.mapper;

import com.Guli.eduService.entity.EduCourse;
import com.Guli.eduService.entity.VO.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublicCourseInfo(String courseId);

}
