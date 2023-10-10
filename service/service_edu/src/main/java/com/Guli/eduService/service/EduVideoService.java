package com.Guli.eduService.service;

import com.Guli.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

}
