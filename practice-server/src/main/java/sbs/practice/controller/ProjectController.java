package sbs.practice.controller;


import com.alibaba.fastjson2.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.enums.JudgeExist;
import sbs.practice.common.exception.ParseObjectException;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.dto.MemberDTO;
import sbs.practice.pojo.dto.ProjectDTO;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.ProjectAndFileVO;
import sbs.practice.pojo.vo.ProjectVO;
import sbs.practice.service.IProjectService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQiDuo
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目接口")
@Slf4j
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    /**
     * 立项时，负责人上传成员,数据以及文件
     * @param projectJson
     * @param file
     * @param membersJson
     * @return
     */
    @ApiOperation("立项上传信息")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("project") String projectJson,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("members") String membersJson) {
        List<MemberDTO> members;
        ProjectDTO project;
        try {
            log.info("projectJSON:{}", projectJson);
            log.info("membersJSON:{}", membersJson);
            // 反序列化ProjectDTO
            project = JSON.parseObject(projectJson, ProjectDTO.class);

            // 反序列化解MemberDTO列表
            members = JSON.parseArray(membersJson, MemberDTO.class);
        } catch (Exception e) {
            throw new ParseObjectException(MessageConstant.PARSE_OBJECT_FAILED);
        }
        // 现在你可以使用project、file和members进行业务逻辑处理了
        projectService.upload(members, project, file);
        return Result.success();
    }

    /**
     * 老师是哪个学院的就是哪个
     * @return
     */

    @ApiOperation("老师查询项目")
    @GetMapping("/select")
    public Result<List<Project>> getByDepart(@RequestParam(required = false) Integer subjectId){
        List<Project> projects = projectService.getAllInDepart(subjectId);
        return Result.success(projects);
    }

    @ApiOperation("老师查询某个人的项目")
    @GetMapping("/get/one")
    public Result<Project> getByProjectId(@RequestParam(value = "id") Integer projectId){
        Project project = projectService.getByProjectId(projectId);
        return Result.success(project);
    }


    @ApiOperation("老师查询当前专题所有项目及文件")
    @GetMapping("/files")
    public Result<List<ProjectAndFileVO>> getFiles(@RequestParam(required = false) Integer subjectId, @RequestParam Integer fileType){
        // 将Project join files
        List<ProjectAndFileVO> results = projectService.getFiles(subjectId, fileType);
        log.info("projectAndFile:{}", results);
        return Result.success(results);
    }

    /**
     * 立项时老师打标签
     * @param labelDTO
     * @return
     */
    @ApiOperation("立项老师打标签")
    @PostMapping("/label")
    public Result<String> label(@RequestBody LabelDTO labelDTO) {
        projectService.labelUpdate(labelDTO);
        return Result.success();
    }



    /**
     * 在立项提交后或者负责人再次点击立项时的finish界面
     * @return
     */

    @ApiOperation("负责人回显项目名与主题名")
    @GetMapping("/name")
    public Result<ProjectVO> getName() {
        ProjectVO result = projectService.getName();
        log.info("projectVO:{}", result);
        return Result.success(result);
    }

    /**
     * 验证负责人是否已经存在项目，如果已存在则跳转至finish界面
     * @return
     */
    @ApiOperation("判断该负责人是否已存在项目")
    @GetMapping("/judge")
    public Result<JudgeExist> judge() {
        JudgeExist isExist = projectService.judge();
        log.info("isExist:{}", isExist);
        return Result.success(isExist);
    }


}
