package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.NewsLabel;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.common.utils.FileIO;
import sbs.practice.pojo.dto.FileDownloadDTO;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Files;
import sbs.practice.mapper.FilesMapper;
import sbs.practice.pojo.entity.Project;
import sbs.practice.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Override
    public String selectFilename(FileDownloadDTO fileDownload) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<Files>();
        queryWrapper.lambda().eq(Files::getProjectId, fileDownload.getId());
        Files file = getOne(queryWrapper);
        String result = file.getFileName();
        return result;
    }

    /**
     * 插入中期文件
     * 要求：
     * 1,系统无储存空间报错
     * 2，project中 leaderId要存在，否则报错
     * @param file
     */

    @Override
    @Transactional
    public void uploadMidTerm(MultipartFile file, FileType type) {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        // 返回文件名
        String fileName = FileIO.uploadFile(file);
        Files files = Files.builder()
                .uploadTime(LocalDateTime.now())
                .projectId(projectId)
                .type(type)
                .fileName(fileName)
                .label(NewsLabel.PENDING)
                .build();
        boolean isSave;
        try {
            isSave = this.save(files);
        }catch (Exception e){
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        if (!isSave)
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
    }

    /**
     * 组长查询中期项目
     * 要求：
     * 1，如果负责人不存在返回空
     * @return
     */
    @Override
    public List<Files> readMidTerm(FileType type) {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        // 查找 type = 2，projectId相同的file返回
        LambdaQueryWrapper<Files> filesWrapper = new QueryWrapper<Files>()
                .lambda()
                .eq(Files::getProjectId, projectId)
                .eq(Files::getType, type);
        return this.list(filesWrapper);
    }
}
