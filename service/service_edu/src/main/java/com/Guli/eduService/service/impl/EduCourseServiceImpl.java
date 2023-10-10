package com.Guli.eduService.service.impl;

import com.Guli.eduService.entity.EduCourse;
import com.Guli.eduService.entity.EduCourseDescription;
import com.Guli.eduService.entity.VO.CourseInfoVo;
import com.Guli.eduService.entity.VO.CoursePublishVo;
import com.Guli.eduService.entity.VO.CourseQuery;
import com.Guli.eduService.mapper.EduCourseMapper;
import com.Guli.eduService.service.EduCourseDescriptionService;
import com.Guli.eduService.service.EduCourseService;
import com.Guli.serviceBase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Autowired
    private EduVideoServiceImpl videoService;

    @Autowired
    private EduChapterServiceImpl chapterService;


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

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo =new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = descService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update ==0) {
            throw new GuliException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        descService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        descService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new GuliException(20001,"删除失败");
        }
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
            String title = courseQuery.getTitle();
            String teacherId = courseQuery.getTeacherId();
            String subjectParentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(teacherId) ) {
                queryWrapper.eq("teacher_id", teacherId);
            }
            if (!StringUtils.isEmpty(subjectParentId)) {
                queryWrapper.ge("subject_parent_id", subjectParentId);
            }
            if (!StringUtils.isEmpty(subjectId)) {
                queryWrapper.ge("subject_id", subjectId);
            }
            baseMapper.selectPage(pageParam, queryWrapper);
    }


}
