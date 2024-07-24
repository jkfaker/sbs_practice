package sbs.practice.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.enums.FileType;
import sbs.practice.pojo.dto.FileDownloadDTO;
import sbs.practice.pojo.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface IFilesService extends IService<Files> {

    String selectFilename(FileDownloadDTO fileDownload);

    void uploadMidTerm(MultipartFile file, FileType type);

    List<Files> readMidTerm(FileType type);
}
