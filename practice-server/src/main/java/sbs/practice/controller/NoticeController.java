package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.NoticeDTO;
import sbs.practice.pojo.entity.Notice;
import sbs.practice.pojo.vo.NoticeVO;
import sbs.practice.service.INoticeService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-09-15
 */
@RestController
@RequestMapping("/notice")
@Slf4j
@Api(tags="消息接口")
@RequiredArgsConstructor
public class NoticeController {

    private final INoticeService noticeService;

    @ApiOperation("学生/老师 获取所有通知")
    @GetMapping("/getAll")
    public Result<List<Notice>> getAll() {
        List<Notice> result = noticeService.getAll();
        return Result.success(result);
    }

    @ApiOperation("老师 添加通知")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody NoticeDTO noticeDTO) {
        log.info("adding notice:{}", noticeDTO);
        Integer noticeId = noticeService.add(noticeDTO);
        return Result.success(noticeId);
    }

    @ApiOperation("老师 删除通知")
    @PostMapping("/delete")
    public Result<String> delete(@RequestBody Notice notice) {
        log.info("deleting notice:{}",notice);
        noticeService.delete(notice);
        return Result.success();
    }

    @ApiOperation("学生/老师 获取通告详情")
    @GetMapping("/detail")
    public Result<NoticeVO> getDetail(@RequestParam Integer id) {
        log.info("getting detail notice:{}",id);
        NoticeVO detail = noticeService.getDetail(id);
        return Result.success(detail);
    }
}
