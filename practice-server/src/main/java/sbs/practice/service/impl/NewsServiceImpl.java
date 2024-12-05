package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.NewsLabel;
import sbs.practice.common.exception.AuthenticationException;
import sbs.practice.common.exception.NewsPermissionException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.NewsMapper;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.NewsVerifyDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.entity.News;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.NewsVO;
import sbs.practice.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {


    private final ISecTeacherService secTeacherService;
    private final IProjectService projectService;
    private final ILeaderService leaderService;
    private final IBaseService baseService;

    /**
     * 分页查询风采展示的项目。
     *要求： 1，label为已通过
     * @param showDTO
     * @return
     */
    @Override
    public PageDTO<NewsVO> getShows(ShowDTO showDTO) {
        // 1.1 分页参数
        Page<News> page = Page.of(showDTO.getPageNo(), showDTO.getPageSize());
        // 1.2 排序参数， 通过OrderItem来指定
        page.addOrder(new OrderItem().setAsc(false).setColumn("id"));
        // 1.3 指定查询条件(label为已通过才能被展示)
        LambdaQueryWrapper<News> newsWrapper;
        // 查询为空
        if (showDTO.getCondition().isEmpty()) {
            newsWrapper = new QueryWrapper<News>()
                    .lambda()
                    .eq(News::getLabel, NewsLabel.PASSED);
        } else {
            // 查询不为空
            // 先查询project表中的 like(leaderName)
            LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                    .like(Project::getLeaderName, showDTO.getCondition());
            List<Project> projects = Db.list(projectWrapper);
            if (!projects.isEmpty()) {
                log.info("project:{}", projects);
                List<Integer> projectIds = projects.stream().map(project -> project.getId()).toList();
                // 处理无结果的情况: 只查询like(title)
                log.info("projectIds:{}", projectIds);
                newsWrapper = new QueryWrapper<News>()
                        .lambda()
                        .eq(News::getLabel, NewsLabel.PASSED)
                        .and(i-> i
                                .like(News::getTitle, showDTO.getCondition())
                                .or()
                                .in(News::getProjectId, projectIds));
            } else {
                newsWrapper = new QueryWrapper<News>()
                        .lambda()
                        .eq(News::getLabel, NewsLabel.PASSED)
                        .like(News::getTitle, showDTO.getCondition());
            }
        }

        //  1.4 分页查询
        Page<News> p = this.page(page, newsWrapper);
        // 2，总条数
        log.info("total="+ p.getTotal());
        // 3，总页数
        log.info("pages="+ p.getPages());
        // 4，分类数据
        List<News> records = p.getRecords();
        log.info("records:{}",records);
        List<NewsVO> list = new ArrayList<>();
        for (News recode : records) {
            Project project = Db.getById(recode.getProjectId(), Project.class);
            NewsVO newsVO = NewsVO.builder()
                    .id(recode.getId())
                    .title(recode.getTitle())
                    .coverImage(recode.getCoverImage())
                    .uploadTime(recode.getUploadTime())
                    .projectId(recode.getProjectId())
                    .leaderName(project.getLeaderName())
                    .build();
            // 获取负责人姓名 by projectId
            list.add(newsVO);
        }
        // 构建 NewsVO的分页查询结果实体
        PageDTO<NewsVO> newsVOPageDTO = PageDTO.<NewsVO>builder()
                .total(p.getTotal())
                .pages(p.getPages())
                .list(list)
                .build();
        return newsVOPageDTO;
    }

    /**
     * 处理学生上传新闻稿
     * 要求： 1，返回newsId
     *2， 必须是校级立项才可上传
     * @param newsDTO
     * @return
     */
    @Override
    public Integer upload(NewsDTO newsDTO) {
        // 查询和验证 projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        // 2, NewsDTO -> News
        if (!projectService.isSchoolLabel(projectId))
            throw new NewsPermissionException(MessageConstant.NEWS_NOT_ACCESS);
        LocalDateTime now = LocalDateTime.now();
        News news = News.builder()
                .title(newsDTO.getTitle())
                .projectId(projectId)
                .coverImage(newsDTO.getCoverImage())
                .text(newsDTO.getText())
                .uploadTime(now)
                .label(NewsLabel.PENDING)
                .build();
        log.info("news:{}", news);
        // 保存
        this.save(news);
        return news.getId();
    }

    /**
     * 查询新闻稿详情
     * 要求： 1，本人 或 已审核
     * @param newsId
     * @return
     */
    @Override
    public News detail(Integer newsId) {
        log.info("newsId:{}", newsId);
        News news = this.getById(newsId);
        Integer newsProjectId;
        try {
            newsProjectId = news.getProjectId();
        } catch (Exception e) {
            throw new SelectException(MessageConstant.SELECT_FAILED);
        }
        // 已审核
        if (news.getLabel() == NewsLabel.PASSED) {
            return news;
        }
        // 未审核 但自己有项目
        Integer currentProjectId = baseService.getProjectIdByCurrentUser();
        // 项目是自己的
        if (newsProjectId.equals(currentProjectId)) {
            return news;
        }
        // 不是自己的
        else {
            throw new AuthenticationException(MessageConstant.AUTHENTICATION_FAILED);
        }
    }

    /**
     * 教师查询新闻稿详情
     * 要求：
     * @param newsId
     * @return
     */
    @Override
    public News teacherDetail(Integer newsId) {
        TokenUtils.verifyTeacher();
        News news = this.getById(newsId);
        try {
            news.getProjectId();
        } catch (Exception e) {
            throw new SelectException(MessageConstant.SELECT_FAILED);
        }
        return news;
    }

    /**
     * TODO
     * @return
     */
    @Override
    public NewsVO getMyself() {
        // 查询和验证 projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        String leaderName = BaseContext.getCurrentUser().getUserName();
        LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getProjectId, projectId);
        News news = this.getOne(newsWrapper);
        NewsVO newsVO = NewsVO.builder()
                .projectId(projectId)
                .coverImage(news.getCoverImage())
                .title(news.getTitle())
                .leaderName(leaderName)
                .text(news.getText())
                .uploadTime(news.getUploadTime())
                .label(news.getLabel())
                .rejectReason(news.getRejectReason())
                .build();
        return newsVO;
    }

    /**
     * 新闻稿不存在返回0
     * 判断是否是校级立项，否则抛出异常
     */
    @Override
    public Integer judge() {
        Integer projectId = baseService.getProjectIdByCurrentUser();
        LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getProjectId, projectId);
        if (!projectService.isSchoolLabel(projectId))
            throw new NewsPermissionException(MessageConstant.NEWS_NOT_ACCESS);
        return 0;
        /*News news = this.getOne(newsWrapper);
        try{
            return news.getId();
        }catch (RuntimeException e) {
            // news不存在
            if (!projectService.isSchoolLabel(projectId))
                throw new NewsPermissionException(MessageConstant.NEWS_NOT_ACCESS);
            return 0;
        }*/
    }

    @Override
    public PageDTO<NewsVO> teacherGetShows(ShowDTO showDTO) {

        Integer departId = secTeacherService.findDepartId();

        // 1.1 分页参数
        Page<News> page = Page.of(showDTO.getPageNo(), showDTO.getPageSize());

        // 1.2 排序参数， 通过OrderItem来指定
        page.addOrder(new OrderItem().setAsc(false).setColumn("id"));

        // 1.3 指定查询条件(label才能被展示)
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getDepartId, departId);
        List<Project> projects = Db.list(projectWrapper);
        List<Integer> projectIds = projects.stream().map(project -> project.getId()).toList();

        // 判断projectId是否为空
        if (projectIds.isEmpty()) {
            return PageDTO.<NewsVO>builder().build();
        }


        LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getLabel, showDTO.getNewsLabel())
                .in(News::getProjectId, projectIds)
                .orderByAsc(News::getUploadTime);


        //  1.4 分页查询
        Page<News> p = this.page(page, newsWrapper);

        // 2，总条数
        log.info("total="+ p.getTotal());

        // 3，总页数
        log.info("pages="+ p.getPages());

        // 4，分类数据
        List<News> records = p.getRecords();

        log.info("records:{}",records);
        List<NewsVO> list = new ArrayList<>();
        for (News recode : records) {
            Project project = Db.getById(recode.getProjectId(), Project.class);
            NewsVO newsVO = NewsVO.builder()
                    .id(recode.getId())
                    .title(recode.getTitle())
                    .coverImage(recode.getCoverImage())
                    .uploadTime(recode.getUploadTime())
                    .projectId(recode.getProjectId())
                    .leaderName(project.getLeaderName())
                    .label(recode.getLabel())
                    .rejectReason(recode.getRejectReason())
                    .build();
            // 获取负责人姓名 by projectId
            list.add(newsVO);
        }
        // 构建 NewsVO的分页查询结果实体
        PageDTO<NewsVO> newsVOPageDTO = PageDTO.<NewsVO>builder()
                .total(p.getTotal())
                .pages(p.getPages())
                .list(list)
                .build();
        return newsVOPageDTO;
    }

    @Override
    public void verify(NewsVerifyDTO newsVerifyDTO) {
        TokenUtils.verifyTeacher();
        LambdaUpdateWrapper<News> updateWrapper = new UpdateWrapper<News>()
                .lambda()
                .eq(News::getId, newsVerifyDTO.getId())
                .set(News::getLabel, newsVerifyDTO.getLabel())
                .set(News::getRejectReason, newsVerifyDTO.getRejectReason())
                .set(News::getExamineTime, LocalDateTime.now());
        this.update(updateWrapper);
    }

    /**
     * 学生查找自己新闻稿
     * @return
     */
    @Override
    public List<NewsVO> getMine() {
        String userId = leaderService.getUserId();
        Integer projectId = baseService.getProjectId(userId);
        List<News> list = getList(projectId);
        List result = new ArrayList<NewsVO>();
        for (News news : list) {
            NewsVO newsVO = BeanUtil.copyProperties(news, NewsVO.class);
            newsVO.setLeaderName(leaderService.selectIdAndName().getLeaderName());
            result.add(newsVO);
        }
        return result;
    }

    /**
     * 通过projectId查找新闻稿
     * @param projectId
     * @return
     */
    private List<News> getList(Integer projectId) {
        LambdaQueryWrapper<News> queryWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getProjectId, projectId);
        List<News> list = this.list(queryWrapper);
        return list;
    }
}
