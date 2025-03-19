package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.pojo.vo.DateVO;
import sbs.practice.service.IBaseService;
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
@RequestMapping("/user/date")
@Api(tags = "打卡接口")
@Slf4j
@RequiredArgsConstructor
public class DateController {


    private final IDateService dateService;
    private final IBaseService baseService;
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
        // 查询和验证 projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        List<Date> result = dateService.show(projectId);
        return Result.success(result);
    }

}
