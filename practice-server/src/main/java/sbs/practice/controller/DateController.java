package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.service.IDateService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */

@RestController
@RequestMapping("/date")
@Api(tags = "打卡接口")
public class DateController {

    @Autowired
    private IDateService dateService;

    /**
     * 学生端向数据库插入学生安全打卡
     * @param dateDTO
     * @return
     */

    @ApiOperation("学生安全打卡")
    @PostMapping("/clock")
    public Result<String> clockIn(@RequestBody DateDTO dateDTO) {
        dateService.clockIn(dateDTO);
        return Result.success();
    }

    /**
     * 学生端展示自己打卡记录
     * @return
     */


    @ApiOperation("学生端个人打卡记录")
    @GetMapping("/show")
    public Result<List<Date>> show() {
        List<Date> result = dateService.show();
        return Result.success(result);
    }

}
