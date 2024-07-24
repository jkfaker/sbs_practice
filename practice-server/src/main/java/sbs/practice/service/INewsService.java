package sbs.practice.service;

import sbs.practice.common.enums.JudgeExist;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.vo.NewsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
public interface INewsService extends IService<News> {

    PageDTO<NewsVO> getShows(ShowDTO showDTO);

    void upload(NewsDTO newsDTO);

    News detail(Integer projectId);

    NewsVO getMyself();

    JudgeExist judge();
}
