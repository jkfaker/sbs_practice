package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.mapper.ProjectMapper;
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

@RestController("teacherDateController")
@RequestMapping("/teacher/date")
@Api(tags = "打卡接口")
@Slf4j
@RequiredArgsConstructor
public class DateController {


    private final IDateService dateService;
    private final ProjectMapper projectMapper;

    /**
     * @param date
     * @param subjectId
     * @return
     */
    @ApiOperation("教师端显示打卡情况")
    @GetMapping
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

    @ApiOperation("教师查看单个人打卡记录")
    @GetMapping("/one")
    public Result<List<Date>> show(@RequestParam Integer projectId) {
        List<Date> result = dateService.show(projectId);
        return Result.success(result);
    }
}
