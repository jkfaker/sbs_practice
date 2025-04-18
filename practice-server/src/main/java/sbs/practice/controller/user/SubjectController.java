package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.entity.Subject;
import sbs.practice.service.ISubjectService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-17
 */
@RestController
@RequestMapping("/user/subject")
@Slf4j
@Api(tags = "主题接口")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    /**
     * 老师/学生 获取所有主题
     *
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
}
