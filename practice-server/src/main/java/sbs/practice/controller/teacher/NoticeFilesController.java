package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.result.Result;
import sbs.practice.service.INoticeFilesService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-12-01
 */
@RestController("teacherNoticeFilesController")
@RequestMapping("/teacher/notice/files")
@RequiredArgsConstructor
@Api(tags = "通知文件接口")
public class NoticeFilesController {

    private final INoticeFilesService noticeFilesService;

    /**
     * 教师上传通知附件
     *
     * @param file
     * @return fileName
     */
    @ApiOperation("教师上传通知附件")
    @PostMapping("/upload")
    public Result<String> uploadFiles(MultipartFile file) {
        String fileName = noticeFilesService.uploadFiles(file);
        return Result.success(fileName);
    }
}
