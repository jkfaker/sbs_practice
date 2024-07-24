package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.enums.UserType;
import sbs.practice.common.result.Result;
import sbs.practice.service.ISecTeacherService;

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
@CrossOrigin(origins = "*")//允许所有来源的请求跨域
@Api(tags = "教师接口")
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
}

