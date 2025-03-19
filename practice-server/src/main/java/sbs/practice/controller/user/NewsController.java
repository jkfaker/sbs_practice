package sbs.practice.controller.user;


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
@RestController
@RequestMapping("/user/news")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "新闻稿接口")

public class NewsController {


    private final INewsService newsService;
    private final IBaseService baseService;
    private final ILeaderService leaderService;
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


    @ApiOperation("学生上传新闻稿")
    @PostMapping("/upload")
    public Result<Integer> upload(@RequestBody NewsDTO newsDTO) {
        log.info("newsDTO:{}", newsDTO);
        Integer newsId =  newsService.upload(newsDTO);
        return Result.success(newsId);
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


    @ApiOperation("查询自己发布的新闻稿")
    @GetMapping("/myself")
    public Result<NewsVO> getMyself(@RequestParam(value = "id") Integer newsId) {
        NewsVO news = newsService.getMyself();
        return Result.success(news);
    }

    @ApiOperation("查询自己发布的新闻稿")
    @GetMapping("/mine")
    public Result<List<NewsVO>> getMine() {
        String userId = leaderService.getUserId();
        Integer projectId = baseService.getProjectId(userId);
        List<NewsVO> result = newsService.getMine(projectId);
        log.info("result:{}" ,result);
        return Result.success(result);
    }

    /**
     *  验证负责人是否已经存在新闻稿，如果已存在则跳转至finish界面
     * @return
     */
    @ApiOperation("判断是否为校级立项")
    @GetMapping("/judge")
    public Result<Integer> judge() {
        Integer isExist = newsService.judge();
        log.info("isExist:{}", isExist);
        return Result.success(isExist);
    }
}
