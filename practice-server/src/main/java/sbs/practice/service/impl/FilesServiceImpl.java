package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.FileConstant;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.constant.StudentOrTeacherConstant;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.enums.NewsLabel;
import sbs.practice.common.exception.FileStorageException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.utils.FileIO;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.FilesMapper;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.entity.Files;
import sbs.practice.service.IBaseService;
import sbs.practice.service.IFilesService;
import sbs.practice.service.IProjectService;
import sbs.practice.service.ISecTeacherService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    private final ISecTeacherService secTeacherService;
    private final FilesMapper filesMapper;
    private final IBaseService baseService;


    /**
     * 老师 打包所有文件 (某学院，某类型)
     * @param subjectId
     * @param fileType
     * @return
     */
    @Override
    public String selectFilename(Integer subjectId, Integer fileType) {
        Integer departId = secTeacherService.findDepartId();
        // 获取List<文件名>
        List<String> fileNameList = filesMapper.selectFileName(departId, subjectId, fileType);
        // 获取文件列表
        List<File> files = fileNameList
                .stream()
                .map((fileName) -> new File(FileConstant.UPLOAD_PATH + fileName))
                .filter(Objects::nonNull)
                .toList();
        // 创建临时文件用于压缩
        Path tempZipFilePath = Paths.get(FileConstant.UPLOAD_PATH, FileConstant.ZIP_FILE_NAME);
        // 压缩文件
        try {
            FileIO.compressFiles(files, tempZipFilePath);
        } catch (Exception e) {
            throw new FileStorageException(MessageConstant.FILE_COMPRESS_ERROR);
        }
        // 返回压缩文件名称
        return FileConstant.ZIP_FILE_NAME;
    }




    /**
     * 上传文件 (立项、中期、结项)
     * 要求：
     * 1,系统无储存空间报错
     * 2，project中 leaderId要存在，否则报错
     * @param file
     */

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, FileType type) {
        // 查询和验证projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        // 文件保存本地
        String fileName = FileIO.uploadFile(file, StudentOrTeacherConstant.STUDENT);
        Files files = Files.builder()
                .uploadTime(LocalDateTime.now())
                .projectId(projectId)
                .type(type)
                .fileName(fileName)
                .label(NewsLabel.PENDING)
                .build();
        boolean isSave;
        try {
            // 录入files表
            isSave = this.save(files);
        }catch (Exception e){
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        if (!isSave)
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
    }

    /**
     * 组长查询项目 （立项、中期、结项）
     * 要求：
     * 1，如果负责人不存在返回空
     * 2，按照时间倒序
     * @return
     */
    @Override
    public List<Files> readFiles(FileType type) {
        // 查询和验证projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        // 查找 type = 2，projectId相同的file返回
        LambdaQueryWrapper<Files> filesWrapper = new QueryWrapper<Files>()
                .lambda()
                .eq(Files::getProjectId, projectId)
                .eq(Files::getType, type)
                .orderByDesc(Files::getUploadTime);
        return this.list(filesWrapper);
    }

    /**
     * 老师 更新file标签
     * @param labelDTO
     */
    @Override
    public void examine(LabelDTO labelDTO) {
        // 验证老师
        TokenUtils.verifyTeacher();

        LambdaUpdateWrapper<Files> filesWrapper = new LambdaUpdateWrapper<>();
        log.info("labelDTO：{}",labelDTO);
        filesWrapper
                .set(Files::getLabel, labelDTO.getLabel())
                .set(Files::getExamineTime, LocalDateTime.now())
                .eq(Files::getId, labelDTO.getId());
        this.update(filesWrapper);
    }
}
