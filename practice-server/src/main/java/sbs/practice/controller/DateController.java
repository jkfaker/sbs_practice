package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.pojo.vo.DateVO;
import sbs.practice.service.IDateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@Slf4j
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
        log.info("dateDTO{}",dateDTO);
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

    /**
     * @param date
     * @param subjectId
     * @return
     */
    @ApiOperation("教师端显示打卡情况")
    @GetMapping("/teacher")
    public Result<List<DateVO>> teacher(
            @RequestParam String date,
            @RequestParam(required = false) Integer subjectId
    ) {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串转换为 LocalDate 对象
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<DateVO> results = dateService.teacher(localDate, subjectId);
        return Result.success(results);
    }
}
