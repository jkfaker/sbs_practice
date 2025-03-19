package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.result.Result;
import sbs.practice.common.utils.FileIO;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.entity.Files;
import sbs.practice.service.IFilesService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author Liu Qiduo
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/user/files")
@Slf4j
@Api(tags = "文件接口")
public class FilesController {

    @Autowired
    private IFilesService filesService;


    /**
     * 项目中期学生端负责人上传文件
     * @param file
     * @return
     */

    @ApiOperation("项目中期上传文件")
    @PostMapping("/middle")
    public Result<String> uploadMidTerm(MultipartFile file) {
        filesService.uploadFile(file, FileType.MIDDLE);
        return Result.success();
    }

    /**
     * 负责人读取中期材料上传记录
     * @return
     */
    @ApiOperation("负责人读取中期材料历史")
    @GetMapping("/middle")
    public Result<List<Files>> readMidTerm() {
        //TODO:
        List<Files> files = filesService.readFiles(FileType.MIDDLE);
        log.info("中期材料返回：{}",files);
        return Result.success(files);
    }

    /**
     * 项目中期学生端负责人上传文件
     * @param file
     * @return
     */
    @ApiOperation("项目结项上传文件")
    @PostMapping("/end")
    public Result<String> uploadEndTerm(MultipartFile file) {
        filesService.uploadFile(file, FileType.END);
        return Result.success();
    }


    /**
     * 负责人读取中期材料上传记录
     * @return
     */
    @ApiOperation("负责人读取结项材料历史")
    @GetMapping("/end")
    public Result<List<Files>> readEndTerm() {
        //TODO:
        List<Files> files = filesService.readFiles(FileType.END);
        log.info("结项材料返回：{}",files);
        return Result.success(files);
    }

    /**
     * 学生编写新闻稿上传图片时触发，后端本地保存图片，返回图片名称
     * @param img
     * @return
     */
    @ApiOperation("图片上传")
    @PostMapping("/images")
    public Result<String> imageUpload(MultipartFile img) {
        String imgName = FileIO.uploadImage(img);
        log.info("图片上传路径：{}",imgName);
        return Result.success(imgName);
    }

}
