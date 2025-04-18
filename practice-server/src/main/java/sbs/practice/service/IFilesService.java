package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.enums.FileType;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.entity.Files;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface IFilesService extends IService<Files> {

    String selectFilename(Integer subjectId, Integer fileType);

    void uploadFile(MultipartFile file, FileType type);

    List<Files> readFiles(FileType type);

    void examineMid(LabelDTO labelDTO);

    List<Files> listAllFilesOfOne(Integer projectId);

}
