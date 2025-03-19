package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.config.MinIOInfo;
import sbs.practice.pojo.dto.AnnounceDTO;
import sbs.practice.pojo.vo.AnnounceVO;
import sbs.practice.service.IAnnounceService;
import sbs.practice.service.IProjectService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-01-07
 */
@RestController("userAnnounceController")
@RequestMapping("/user/announce")
@RequiredArgsConstructor
@Api(tags = "消息接口")
@Slf4j
public class AnnounceController {
    private final IAnnounceService announceService;
    private final IProjectService projectService;
    /**
     * 统计当前学生未读消息总数
     * @return 消息总数
     */
    @GetMapping("/count")
    @ApiOperation("未读消息总数")
    public Result<Long> countUnreadAnnounceOfUser() {
        Long count = announceService.countUnreadAnnounceOfUser();
        return Result.success(count);
    }

    /**
     * 学生 获得发给自己所有消息
     * @return 消息列表
     */
    @GetMapping("/all/list")
    @ApiOperation("学生消息列表")
    public Result<List<AnnounceVO>> getAllAnnounceOfUser() {
        List<AnnounceVO> announceVOList = announceService.getAllAnnounceOfUser();
        return Result.success(announceVOList);
    }


    @PostMapping("/all/read")
    @ApiOperation("学生消息全部已读")
    public Result<String> studentAllRead() {
        announceService.studentAllRead();
        return Result.success();
    }

    @PostMapping("/read/{id}")
    @ApiOperation("学生已读消息")
    public Result<String> studentOneRead(@PathVariable Integer id) {
        announceService.studentOneRead(id);
        return Result.success();
    }


}
