package sbs.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.DeleteException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.NoticeMapper;
import sbs.practice.pojo.dto.NoticeDTO;
import sbs.practice.pojo.entity.Notice;
import sbs.practice.pojo.vo.NoticeVO;
import sbs.practice.service.INoticeService;
import sbs.practice.service.ISecTeacherService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-09-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    private final ISecTeacherService secTeacherService;
    private final NoticeFilesServiceImpl noticeFilesService;

    /**
     * 学生老师 获取通告列表
     * 要求：1，列表只取text前14个字符
     * 2, 用正则 提出html的标签
     * @return notice
     */
    @Override
    public List<Notice> getAll() {
        return this.list()
                .stream()
                .map(notice -> {
                    // 原始html
                    String originalText = notice.getText();
                    String truncatedText;
                    Pattern pattern1 = Pattern.compile("<[^>]*>");
                    String tempText = pattern1.matcher(originalText).replaceAll("").trim();
                    // 使用正则表达式匹配前14个包含标点符号的字符
                    log.info("tempText:{}",tempText);
                    // 直接截取前14个字符
                    if (tempText.length() > 14) {
                        truncatedText = tempText.substring(0, 30);
                    } else {
                        // 如果原始文本长度小于或等于14，则直接使用原字符串
                        truncatedText = tempText;
                    }
                    log.info("truncatedText:{}", truncatedText);
                    return notice.setText(truncatedText);
                })
                .collect(Collectors.toList());
    }

    /**
     * 教师添加通告
     * 要求：1，数据未存入时抛出异常
     * 2，获取教师工号
     * 3，添加createTime和UpdateTime
     * 4, 如果有文件，则上传文件
     *
     * @param noticeDTO
     * @return
     */
    @Override
    public Integer add(NoticeDTO noticeDTO) {
        String teacherId = secTeacherService.getTeacherId();
        LocalDateTime now = LocalDateTime.now();
        Notice notice = Notice
                .builder()
                .teacherId(teacherId)
                .title(noticeDTO.getTitle())
                .text(noticeDTO.getText())
                .createTime(now)
                .updateTime(now)
                .build();
        if (!this.save(notice)){
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        Integer noticeId = notice.getId();
        if (noticeDTO.getFileName() != null && noticeDTO.getFileName().length() != 0){
            noticeFilesService.insertFiles(noticeDTO.getFileName(), noticeId);
        }
        return noticeId;
    }
    /**
     * 教师删除通告
     * 要求：1，数据未删除时抛出异常
     * 2, 先删除文件（如果有）
     * @param notice
     */
    @Override
    public void delete(Notice notice) {
        TokenUtils.verifyTeacher();
        log.info("deleting notice:{}",notice);
        boolean delete = noticeFilesService.delete(notice.getId());
        if (!this.removeById(notice)) {
            throw new DeleteException(MessageConstant.DELETE_FAILED);
        }
    }

    /**
     * 页面详情
     * @param id
     * @return NoticeVO
     */
    @Override
    public NoticeVO getDetail(Integer id) {
        Notice notice = this.getById(id);
        String teacherName = secTeacherService.getTeacherName(notice.getTeacherId());
        String noticeFileName = noticeFilesService.getNoticeFileName(id);
        NoticeVO noticeVO = NoticeVO
                .builder()
                .title(notice.getTitle())
                .text(notice.getText())
                .teacherName(teacherName)
                .createTime(notice.getCreateTime())
                .updateTime(notice.getUpdateTime())
                .build();
        if (noticeFileName != null) noticeVO.setFileName(noticeFileName);
        return noticeVO;
    }

}
