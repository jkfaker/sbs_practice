package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.dto.NewsDTO;
import sbs.practice.pojo.dto.NewsVerifyDTO;
import sbs.practice.pojo.dto.PageDTO;
import sbs.practice.pojo.dto.ShowDTO;
import sbs.practice.pojo.entity.News;
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

    Integer upload(NewsDTO newsDTO);

    News detail(Integer newsId);

    News teacherDetail(Integer newsId);

    NewsVO getMyself();

    Integer judge();

    PageDTO<NewsVO> teacherGetShows(ShowDTO showDTO);

    void verify(NewsVerifyDTO newsVerifyDTO);

    List<NewsVO> getMine(Integer projectId);
}
