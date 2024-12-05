package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.enums.UserType;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.entity.SecTeacher;
import sbs.practice.service.ISecTeacherService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/teacher")
@Api(tags = "教师接口")
@Slf4j
public class SecTeacherController {
    @Autowired
    private ISecTeacherService secTeacherService;

    // 1,老师，2,学生
    @PostMapping("/login")
    @ApiOperation("验证老师登录")
    public Result<UserType> login() {
        UserType result = secTeacherService.login();
        return Result.success(result);
    }

    /**
     * 管理新增学院负责人
     * @param secTeacher
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加老师")
    public Result<String> upload(@RequestBody SecTeacher secTeacher) {
        secTeacherService.add(secTeacher);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除老师")
    public Result<String> delete(@RequestBody SecTeacher secTeacher) {
        log.info("subject:{}",secTeacher);
        secTeacherService.delete(secTeacher);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新老师")
    public Result<String> update(@RequestBody SecTeacher secTeacher) {
        secTeacherService.renew(secTeacher);
        return Result.success();
    }

    @GetMapping
    @ApiOperation("获取所有老师")
    public Result<List<SecTeacher>> getAll() {
//        TokenUtils.verifyTeacher();
        List<SecTeacher> teachers = secTeacherService.list();
        return Result.success(teachers);
    }

}

