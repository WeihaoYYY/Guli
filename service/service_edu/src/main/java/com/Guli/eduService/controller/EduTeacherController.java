package com.Guli.eduService.controller;


import com.Guli.eduService.entity.EduTeacher;
import com.Guli.eduService.entity.VO.TeacherQuery;
import com.Guli.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Weihao
 * @since 2023-07-07
 */
@Api(description = "讲师管理") //Swagger注解，等效于@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")
@CrossOrigin
public class EduTeacherController {

    //查询讲师表所有数据
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //int a = 10 / 0;  //测试全局异常处理
        return R.ok().data("items", teacherService.list(null));  //null表示查询所有
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")  //逻辑删除
    public R removeTeacherById(@ApiParam(name = "id", value = "讲师ID") @PathVariable String id){
        //不存在的id删除，看源码可以得出结论，你传递过去的时候是字符串，被解析后交给数据库处理，没有成功删除返回0，源码只要>=0并且!=null就算成功
        return teacherService.removeById(id) ? R.ok() : R.error();
    }

    @ApiOperation(value = "分页讲师列表")  //这里有争议，说业务应该放到service里面
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页记录数") @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

        return  R.ok().data("result",pageTeacher);
        //return R.ok().data("total", total).data("rows", records);
    }

    //条件查询带分页的方法
    @ApiOperation(value = "条件查询带分页的方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页") @PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页记录数") @PathVariable long limit,
                                  @ApiParam(name = "teacherQuery", value = "查询对象")
                                      @RequestBody(required = false) TeacherQuery teacherQuery){  //这里的requestbody也可以不要，post改成get
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询，判断条件值是否为空，如果不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);  //ge:大于等于 greater than or equal
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);  //le:小于等于  //less than or equal
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")  //@RequestBody 表明传递的是json数据，因为EduTeacher是一个对象，前端传不了对象
    public R addTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象") @RequestBody EduTeacher eduTeacher){
        return teacherService.save(eduTeacher) ? R.ok() : R.error();
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID") @PathVariable String id){
        return R.ok().data("teacher", teacherService.getById(id));
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象") @RequestBody EduTeacher eduTeacher){
        //实际开发中一定不能这样写，要使用dto去接收前端传输过来的数据，直接使用实体类懂技术的会直接自己发送Ajax请求添加不能传输的字段
        return teacherService.updateById(eduTeacher) ? R.ok() : R.error();
    }



}

