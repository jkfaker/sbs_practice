package sbs.practice.controller.teacher;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.result.Result;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.InstructorMapper;
import sbs.practice.pojo.entity.Instructor;
import sbs.practice.pojo.vo.MemberVO;
import sbs.practice.service.IBaseService;
import sbs.practice.service.IInstructorService;
import sbs.practice.service.ISecTeacherService;

import java.util.List;

/**
 * <p>
 * 指导老师 前端控制器
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-04-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher/instructor")
public class InstructorController {

    private final IInstructorService instructorService;


    @GetMapping("/list/projectId")
    public Result<List<Instructor>> getListByProjectId(@RequestParam Integer projectId) {
        // 验证教师
        TokenUtils.verifyTeacher();
        List<Instructor> instructors = instructorService.getListByProjectId(projectId);
        return Result.success(instructors);
    }

}
