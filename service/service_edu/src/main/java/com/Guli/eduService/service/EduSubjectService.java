package com.Guli.eduService.service;

import com.Guli.eduService.entity.EduSubject;
import com.Guli.eduService.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Weihao
 * @since 2023-08-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();

    //返回所有分类数据，返回要求的json数据格式


}
