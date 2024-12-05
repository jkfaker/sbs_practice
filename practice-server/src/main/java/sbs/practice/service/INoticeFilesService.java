package sbs.practice.service;

import org.springframework.web.multipart.MultipartFile;
import sbs.practice.pojo.entity.NoticeFiles;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-12-01
 */
public interface INoticeFilesService extends IService<NoticeFiles> {

    String uploadFiles(MultipartFile file);

    void insertFiles(String fileName, Integer noticeId);

    boolean delete(Integer notice_id);

    String getNoticeFileName(Integer notice_id);
}
