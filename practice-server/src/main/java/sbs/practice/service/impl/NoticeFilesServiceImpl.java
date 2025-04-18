package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.constant.StudentOrTeacherConstant;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.utils.FileIO;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.NoticeFilesMapper;
import sbs.practice.pojo.entity.NoticeFiles;
import sbs.practice.service.INoticeFilesService;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-12-01
 */
@Service
@RequiredArgsConstructor
public class NoticeFilesServiceImpl extends ServiceImpl<NoticeFilesMapper, NoticeFiles> implements INoticeFilesService {

    private final FileIO fileIO;

    /**
     * 老师上传通知附件
     *
     * @param file
     * @return fileName
     */
    @Override
    public String uploadFiles(MultipartFile file) {
        TokenUtils.verifyTeacher();
        String fileName = fileIO.uploadFile(file, StudentOrTeacherConstant.TEACHER);
        return fileName;
    }

    /**
     * 老师 将通知附件保存到数据库
     *
     * @param fileName, noticeId
     */
    @Override
    public void insertFiles(String fileName, Integer noticeId) {
        NoticeFiles noticeFiles = NoticeFiles
                .builder()
                .noticeId(noticeId)
                .fileName(fileName)
                .uploadTime(LocalDateTime.now())
                .build();
        if (!this.save(noticeFiles)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
    }


    /**
     * 教师删除通告文件
     * 要求：1，数据未删除时抛出异常
     *
     * @param notice_id
     * @return
     */
    @Override
    public boolean delete(Integer notice_id) {
        TokenUtils.verifyTeacher();
        LambdaQueryWrapper<NoticeFiles> queryWrapper = new QueryWrapper<NoticeFiles>()
                .lambda()
                .eq(NoticeFiles::getNoticeId, notice_id);
        return this.remove(queryWrapper);
    }

    /**
     * 学生/老师 获取附件名
     * 要求： 如果没有查到数据，则返回null
     *
     * @param notice_id
     * @return
     */
    @Override
    public String getNoticeFileName(Integer notice_id) {
        LambdaQueryWrapper<NoticeFiles> queryWrapper = new QueryWrapper<NoticeFiles>()
                .lambda()
                .eq(NoticeFiles::getNoticeId, notice_id);
        NoticeFiles noticeFile = this.getOne(queryWrapper);
        if (noticeFile != null && noticeFile.getFileName() != null && noticeFile.getFileName().length() != 0)
            return noticeFile.getFileName();
        return null;
    }
}
