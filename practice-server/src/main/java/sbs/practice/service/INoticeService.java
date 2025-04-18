package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.dto.NoticeDTO;
import sbs.practice.pojo.entity.Notice;
import sbs.practice.pojo.vo.NoticeVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-09-15
 */
public interface INoticeService extends IService<Notice> {

    List<Notice> getAll();

    Integer add(NoticeDTO noticeDTO);

    void delete(Notice notice);

    NoticeVO getDetail(Integer id);
}
