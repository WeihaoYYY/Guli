package com.Guli.eduService.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {

    private String id;

    private String title;

    //一个一级分类里面有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
