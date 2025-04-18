package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.dto.AnnounceDTO;
import sbs.practice.pojo.entity.Announce;
import sbs.practice.pojo.vo.AnnounceVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-01-07
 */


public interface IAnnounceService extends IService<Announce> {


    Long countUnreadAnnounceOfUser();

    List<AnnounceVO> getAllAnnounceOfUser();

    void sentMessageToStudent(AnnounceDTO announceDTO);

    void sentSystemMessage(AnnounceDTO announceDTO);

    void studentAllRead();

    void studentOneRead(Integer id);

    List<AnnounceVO> getTeacherAnnounce(Integer projectId);
}
