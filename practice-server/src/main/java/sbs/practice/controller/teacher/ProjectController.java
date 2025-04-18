package sbs.practice.controller.teacher;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.ProjectAndFileVO;
import sbs.practice.service.IProjectService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuQiDuo
 * @since 2024-06-26
 */
@RestController("teacherProjectController")
@RequestMapping("/teacher/project")
@Api(tags = "项目接口")
@Slf4j
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    /**
     * 老师是哪个学院的就是哪个
     *
     * @return
     */

    @ApiOperation("老师查询项目")
    @GetMapping("/select")
    public Result<List<Project>> getByDepart(@RequestParam(required = false) Integer subjectId) {
        List<Project> projects = projectService.getAllInDepart(subjectId);
        return Result.success(projects);
    }

    @ApiOperation("老师查询某个人的项目")
    @GetMapping("/one")
    public Result<Project> getByProjectId(@RequestParam(value = "id") Integer projectId) {
        Project project = projectService.getByProjectId(projectId);
        return Result.success(project);
    }

    /**
     * @param fileType    立项，中期，结项文件
     * @param subjectId   用于定位主题
     * @param projectName 用于模糊查询
     * @return
     */
    @ApiOperation("老师查询当前专题所有项目及文件")
    @GetMapping("/files")
    public Result<List<ProjectAndFileVO>> getFiles(@RequestParam Integer fileType, @RequestParam(required = false) Integer subjectId, @RequestParam(required = false) String projectName) {
        // 将Project join files
        List<ProjectAndFileVO> results = projectService.getFiles(subjectId, fileType, projectName);
        log.info("projectAndFile:{}", results);
        return Result.success(results);
    }

    /**
     * 立项时老师打标签
     *
     * @param labelDTO
     * @return
     */
    @ApiOperation("立项老师打标签")
    @PostMapping("/label")
    public Result<String> label(@RequestBody LabelDTO labelDTO) {
        projectService.labelUpdate(labelDTO);
        return Result.success();
    }
}
