package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.entity.Notice;
import sbs.practice.pojo.vo.NoticeVO;
import sbs.practice.service.INoticeService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-09-15
 */
@RestController
@RequestMapping("/user/notice")
@Slf4j
@Api(tags = "通知接口")
@RequiredArgsConstructor
public class NoticeController {

    private final INoticeService noticeService;

    @ApiOperation("学生/老师 获取所有通知")
    @GetMapping("/all")
    public Result<List<Notice>> getAll() {
        List<Notice> result = noticeService.getAll();
        return Result.success(result);
    }

    @ApiOperation("学生/老师 获取通告详情")
    @GetMapping("/detail")
    public Result<NoticeVO> getDetail(@RequestParam Integer id) {
        log.info("getting detail notice:{}", id);
        NoticeVO detail = noticeService.getDetail(id);
        return Result.success(detail);
    }
}
