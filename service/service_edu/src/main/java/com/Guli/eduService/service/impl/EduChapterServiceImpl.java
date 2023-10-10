package com.Guli.eduService.service.impl;

import com.Guli.eduService.entity.EduChapter;
import com.Guli.eduService.entity.EduVideo;
import com.Guli.eduService.entity.chapter.ChapterVo;
import com.Guli.eduService.entity.chapter.VideoVo;
import com.Guli.eduService.mapper.EduChapterMapper;
import com.Guli.eduService.service.EduChapterService;
import com.Guli.serviceBase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Weihao
 * @since 2023-10-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoServiceImpl videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询课程里面的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装的集合
        List<ChapterVo> finallList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //将edChapter对象复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终的list集合中
            finallList.add(chapterVo);

            //创建集合，用于封装章节中的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放在小节的集合中
                    videoList.add(videoVo);
                }
            }
            //把封装之后的小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finallList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id查询小节表，如果查询到数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);

        if (count > 0) {//查询出小节，不进行删除
            throw new GuliException(20001,"存在小节，不能删除");
        }else{//没有查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功 1>0  0>0
            return result>0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
