package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.entity.Files;
import sbs.practice.service.IFilesService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Liu Qiduo
 * @since 2024-06-26
 */
@RestController("teacherFilesController")
@RequestMapping("/teacher/files")
@Slf4j
@Api(tags = "文件接口")
public class FilesController {

    @Autowired
    private IFilesService filesService;

    /**
     * 后端提供文件下载路径
     *
     * @return
     */
    @ApiOperation("教师批量下载文件")
    @GetMapping("/download")
    public Result<String> download(@RequestParam Integer subjectId, @RequestParam Integer fileType) {
        String fileUrl = filesService.selectFilename(subjectId, fileType);
        return Result.success(fileUrl);
    }

    @ApiOperation("老师审核中期文件")
    @PostMapping("/mid/label")
    public Result<String> examineMid(@RequestBody LabelDTO labelDTO) {
        filesService.examineMid(labelDTO);
        return Result.success();
    }

    @ApiOperation("老师审核结项文件")
    @PostMapping("/end/label")
    public Result<String> examineEnd(@RequestBody LabelDTO labelDTO) {
        filesService.examineMid(labelDTO);
        return Result.success();
    }

    @ApiOperation("老师读取某个项目所有文件资料")
    @GetMapping("/all/one")
    public Result<List<Files>> readAllOfOne(@RequestParam Integer projectId) {
        List<Files> files = filesService.listAllFilesOfOne(projectId);
        return Result.success(files);
    }
}
