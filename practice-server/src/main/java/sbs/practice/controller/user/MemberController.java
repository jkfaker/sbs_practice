package sbs.practice.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.vo.MemberVO;
import sbs.practice.service.IMemberService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/user/member")
@Api(tags = "成员接口")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @GetMapping
    @ApiOperation("该项目所有成员")
    public Result<List<MemberVO>> getByLeader() {
        List<MemberVO> members = memberService.getByLeader();
        return Result.success(members);
    }

}
