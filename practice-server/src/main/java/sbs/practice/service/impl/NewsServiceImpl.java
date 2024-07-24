package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.JudgeExist;
import sbs.practice.common.enums.NewsLabel;
import sbs.practice.common.exception.SelectException;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.News;
import sbs.practice.mapper.NewsMapper;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.NewsVO;
import sbs.practice.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

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
        if (showDTO.getCondition().isEmpty()) {
            newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getLabel, NewsLabel.PASSED);
        } else {
            // 先查询project表中的 like(leaderName)
            LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                    .like(Project::getLeaderName, showDTO.getCondition());
            Project project = Db.getOne(projectWrapper);
            // 处理无结果的情况: 只查询like(title)
            log.info("project:{}",project);
            try {
                Integer projectId = project.getId();
                newsWrapper = new QueryWrapper<News>()
                        .lambda()
                        .eq(News::getLabel, NewsLabel.PASSED)
                        .like(News::getTitle, showDTO.getCondition())
                        .or()
                        .eq(News::getProjectId, projectId);
            }catch (NullPointerException e)    {
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
     * @param newsDTO
     */
    @Override
    public void upload(NewsDTO newsDTO) {
        // 1， 获取projectId
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 获取project
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        // 2, NewsDTO -> News
        LocalDateTime now = LocalDateTime.now();
        News news = News.builder()
                .title(newsDTO.getTitle())
                .projectId(projectId)
                .coverImage(newsDTO.getCoverImage())
                .text(newsDTO.getText())
                .uploadTime(now)
                .label(NewsLabel.PENDING)
                .build();
        // 保存
        this.save(news);
    }

    @Override
    public News detail(Integer projectId) {
        LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getProjectId, projectId);
        News news = this.getOne(newsWrapper);
        return news;
    }

    @Override
    public NewsVO getMyself() {
        // 1， 获取projectId
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 获取project
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        Integer projectId;
        String leaderName;
        try {
            leaderName = project.getLeaderName();
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                .lambda()
                .eq(News::getProjectId, projectId);
        News news = this.getOne(newsWrapper);
        NewsVO newsVO = NewsVO.builder()
                .projectId(projectId)
                .coverImage(news.getCoverImage())
                .title(news.getTitle())
                .leaderName(leaderName)
                .uploadTime(news.getUploadTime())
                .build();
        return newsVO;
    }

    @Override
    public JudgeExist judge() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 获取project
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        // 判断project是否为空
        if (project.getId() == null || project.getId().toString().isEmpty())
            return JudgeExist.NOT_EXIST;
        else {
            LambdaQueryWrapper<News> newsWrapper = new QueryWrapper<News>()
                    .lambda()
                    .eq(News::getProjectId, project.getId());
            News news = this.getOne(newsWrapper);
            if (news.getId() == null || project.getId().toString().isEmpty()) {
                return JudgeExist.NOT_EXIST;
            } else {
                return JudgeExist.EXIST;
            }
        }
    }
}
