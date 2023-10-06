package com.Guli.eduService.service.impl;

import com.Guli.eduService.entity.EduSubject;
import com.Guli.eduService.entity.excel.SubjectData;
import com.Guli.eduService.entity.subject.OneSubject;
import com.Guli.eduService.entity.subject.TwoSubject;
import com.Guli.eduService.listener.SubjectExcelListener;
import com.Guli.eduService.mapper.EduSubjectMapper;
import com.Guli.eduService.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Weihao
 * @since 2023-08-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapperOne);

        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> eduSubjectsTwo = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        for(int i = 0; i < eduSubjects.size(); i++){
            EduSubject eduSubject = eduSubjects.get(i);

            OneSubject one = new OneSubject();

            //第一种方法，手动封装，但是如果遇到属性多的时候，就会很麻烦
//            one.setId(eduSubject.getId());
//            one.setTitle(eduSubject.getTitle());

            //第二种方法，使用BeanUtils工具类，把eduSubject里面获取出来的值，放到oneSubject对象里面
            BeanUtils.copyProperties(eduSubject, one);



//            //多个OneSubject放到findSubjectList里面
            finalSubjectList.add(one);

            List<TwoSubject> listTwo = new ArrayList<>();

            //封装二级分类
            for (EduSubject subjectTwo : eduSubjectsTwo) {

                if (eduSubject.getId().equals(subjectTwo.getParentId())) {
                    TwoSubject two = new TwoSubject();
                    BeanUtils.copyProperties(subjectTwo, two);
                    listTwo.add(two);
                }
            }

            one.setChildren(listTwo);

        }



        return finalSubjectList;


    }

}
