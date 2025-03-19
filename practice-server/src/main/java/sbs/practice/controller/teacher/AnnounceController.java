package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.AnnounceDTO;
import sbs.practice.pojo.vo.AnnounceVO;
import sbs.practice.service.IAnnounceService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-01-07
 */
@RestController("teacherAnnounceController")
@RequestMapping("/teacher/announce")
@RequiredArgsConstructor
@Api(tags = "消息接口")
@Slf4j
public class AnnounceController {
    private final IAnnounceService announceService;


    @PostMapping("/send")
    @ApiOperation("老师发送信息给学生")
    public Result<String> sentMessageToStudent(@RequestBody AnnounceDTO announceDTO) {
        announceService.sentMessageToStudent(announceDTO);
        return Result.success();
    }

    @ApiOperation("获取老师向该学生发送过的全部信息")
    @GetMapping("/one")
    public Result<List<AnnounceVO>> getAllAnnounceOfOne(@RequestParam Integer projectId) {

        List<AnnounceVO> results = announceService.getTeacherAnnounce(projectId);
        return Result.success(results);
    }
}
