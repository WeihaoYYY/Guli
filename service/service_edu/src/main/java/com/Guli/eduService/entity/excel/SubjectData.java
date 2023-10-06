package com.Guli.eduService.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    @ExcelProperty(index = 0) //设置excel表头对应的列
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;



}
