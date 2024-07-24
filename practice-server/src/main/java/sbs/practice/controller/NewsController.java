package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sbs.practice.common.enums.JudgeExist;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.entity.News;
import sbs.practice.pojo.vo.NewsVO;
import sbs.practice.service.INewsService;

import javax.naming.InsufficientResourcesException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
@RestController
@RequestMapping("/news")
@Slf4j
@Api(tags = "新闻稿接口")
public class NewsController {

    @Autowired
    private INewsService newsService;
    /**
     * 分页查询风采展示
     * @return
     */
    @ApiOperation("风采展示读取")
    @GetMapping("/shows")
    public Result<PageDTO<NewsVO>> getShows(ShowDTO showDTO) {
        log.info("show.condition:{}",showDTO.getCondition());
        PageDTO<NewsVO> results = newsService.getShows(showDTO);
        log.info("results:{}",results);
        return Result.success(results);
    }

    @ApiOperation("老师审核新闻稿")
    @PostMapping("/verify")
    public Result<String> verify(@RequestBody News news) {
        // TODO
        return Result.success();
    }

    @ApiOperation("学生上传新闻稿")
    @PostMapping("/upload")
    public Result<String> upload(@RequestBody NewsDTO newsDTO) {
        newsService.upload(newsDTO);
        return Result.success();
    }

    @ApiOperation("风采展示详情")
    @GetMapping("/detail")
    public Result<News> detail(@RequestParam Integer projectId) {
        News result = newsService.detail(projectId);
        return Result.success(result);
    }

    @ApiOperation("查询自己发布的新闻稿")
    @GetMapping("/myself")
    public Result<NewsVO> getMyself() {
        NewsVO news = newsService.getMyself();
        return Result.success(news);
    }

    /**
     *  验证负责人是否已经存在新闻稿，如果已存在则跳转至finish界面
     * @return
     */
    @ApiOperation("判断该负责人是否已发布新闻稿")
    @GetMapping("/judge")
    public Result<JudgeExist> judge() {
        JudgeExist isExist = newsService.judge();
        log.info("isExist:{}", isExist);
        return Result.success(isExist);
    }


}
