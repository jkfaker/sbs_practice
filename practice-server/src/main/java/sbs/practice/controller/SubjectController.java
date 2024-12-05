package sbs.practice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.entity.Subject;
import sbs.practice.service.ISubjectService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-17
 */
@RestController
@RequestMapping("/subject")
@Slf4j
@Api(tags = "主题接口")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    /**
     * 老师/学生 获取所有主题
      * @return
     */
    @GetMapping
    @ApiOperation("获取所有主题")
    public Result<List<Subject>> getAll() {
        List<Subject> subjects = subjectService.list();
        return Result.success(subjects);
    }

    /**
     * 老师/学生 获取主题数量
     *
     * @return
     */
    @GetMapping("/count")
    @ApiOperation("获取主题数量")
    public Result<Integer> getCount() {
        int count = (int) subjectService.count();
        return Result.success(count);
    }

    /**
     * 老师新增主题
     * @param subject
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加主题")
    public Result<String> upload(@RequestBody Subject subject) {

        subjectService.add(subject);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除主题")
    public Result<String> delete(@RequestBody Subject subject) {
        log.info("subject:{}",subject);
        subjectService.delete(subject);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新主题")
    public Result<String> update(@RequestBody Subject subject) {
        subjectService.renew(subject);
        return Result.success();
    }
}
