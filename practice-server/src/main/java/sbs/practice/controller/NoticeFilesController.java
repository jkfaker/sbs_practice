package sbs.practice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.result.Result;
import sbs.practice.service.INoticeFilesService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-12-01
 */
@RestController
@RequestMapping("/notice/files")
@RequiredArgsConstructor
public class NoticeFilesController {

    private final INoticeFilesService noticeFilesService;

    /**
     * 教师上传通知附件
     * @param file
     * @return fileName
     */
    @PostMapping("/upload")
    public Result<String> uploadFiles(MultipartFile file) {
        String fileName = noticeFilesService.uploadFiles(file);
        return Result.success(fileName);
    }
}
