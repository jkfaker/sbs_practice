package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.mapper.NewsMapper;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.NewsVerifyDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.entity.News;
import sbs.practice.pojo.vo.NewsVO;
import sbs.practice.service.IBaseService;
import sbs.practice.service.ILeaderService;
import sbs.practice.service.INewsService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
@RestController("teacherNewsController")
@RequestMapping("/teacher/news")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "新闻稿接口")
public class NewsController {

    private final INewsService newsService;
    /**
     * 分页查询风采展示
     * @return
     */
    @ApiOperation("读取风采展示")
    @GetMapping("/shows")
    public Result<PageDTO<NewsVO>> getShows(ShowDTO showDTO) {
        log.info("show.condition:{}",showDTO.getCondition());
        PageDTO<NewsVO> results = newsService.getShows(showDTO);
        log.info("results:{}",results);
        return Result.success(results);
    }
    /**
     * 所有人 查看已通过的风采详情
     * @param newsId
     * @return
     */
    @ApiOperation("风采展示详情")
    @GetMapping("/detail")
    public Result<News> detail(@RequestParam(value = "id") Integer newsId) {
        News result = newsService.detail(newsId);
        log.info("result:{}",result);
        return Result.success(result);
    }


    @ApiOperation("教师 读取新闻稿")
    @GetMapping("/verify/shows")
    public Result<PageDTO<NewsVO>> teacherGetShows(ShowDTO showDTO) {
        log.info("show.condition:{}",showDTO.getCondition());
        PageDTO<NewsVO> results = newsService.teacherGetShows(showDTO);
        log.info("results:{}",results);
        return Result.success(results);
    }

    @ApiOperation("老师审核新闻稿")
    @PostMapping("/verify")
    public Result<String> verify(@RequestBody NewsVerifyDTO newsVerifyDTO) {
        // TODO
        newsService.verify(newsVerifyDTO);
        return Result.success();
    }

    /**
     * 老师 查看待审核风采详情
     * @param newsId
     * @return
     */
    @ApiOperation("风采展示详情")
    @GetMapping("/verify/detail")
    public Result<News> teacherDetail(@RequestParam(value = "id") Integer newsId) {
        News result = newsService.teacherDetail(newsId);
        log.info("result:{}",result);
        return Result.success(result);
    }

    @ApiOperation("查询某人发布的新闻稿")
    @GetMapping("/one")
    public Result<List<NewsVO>> getMine(@RequestParam Integer projectId) {
        List<NewsVO> result = newsService.getMine(projectId);
        log.info("result:{}" ,result);
        return Result.success(result);
    }
}
