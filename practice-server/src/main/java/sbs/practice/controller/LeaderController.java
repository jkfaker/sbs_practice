package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.vo.LeaderVO;
import sbs.practice.service.ILeaderService;

@Slf4j
@RestController
@RequestMapping("/leader")
@Api(tags = "负责人接口")
public class LeaderController {


    @Autowired
    private ILeaderService leaderService;

    /**
     * 立项回显负责人信息
     * @return
     */
    @ApiOperation("回显负责人信息")
    @GetMapping
    public Result<LeaderVO> selectIdAndName() {
        LeaderVO leader = leaderService.selectIdAndName();
        log.info("回调负责人信息：{}",leader);
        return Result.success(leader);
    }
}
