package com.Guli.eduService.service.impl;

import com.Guli.eduService.entity.EduCourse;
import com.Guli.eduService.entity.EduCourseDescription;
import com.Guli.eduService.entity.VO.CourseInfoVo;
import com.Guli.eduService.mapper.EduCourseMapper;
import com.Guli.eduService.service.EduCourseDescriptionService;
import com.Guli.eduService.service.EduCourseService;
import com.Guli.serviceBase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);//将eduCourse对象插入到数据库中

        if(insert <= 0) {
            //添加失败
            throw new GuliException(20001, "Failed to add course");
        }

        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription desc = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,desc);
        desc.setId(cid);
        descService.save(desc);

        return cid;

    }
}
