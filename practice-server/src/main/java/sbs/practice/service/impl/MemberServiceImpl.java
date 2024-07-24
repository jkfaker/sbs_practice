package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.exception.SelectException;
import sbs.practice.mapper.MemberMapper;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Member;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.MemberVO;
import sbs.practice.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Override
    public List<MemberVO> getByLeader() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 根据userId查询projectId
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }

        // 根据projectId查找成员
        LambdaQueryWrapper<Member> memberWrapper = Wrappers.lambdaQuery(Member.class)
                .eq(Member::getProjectId, projectId);
        List<Member> members = Db.list(memberWrapper);

        // 先添加负责人信息，把负责人放在第一个
        MemberVO leader = new MemberVO(project.getLeaderId(), project.getLeaderName());
        List<MemberVO> results = new ArrayList<>();
        results.add(leader);
        // 通过steam流添加其他队员
        List<MemberVO> membersVO = members.stream()
                .map(member -> new MemberVO(member.getMemberId(), member.getMemberName()))
                .collect(Collectors.toList());
        results.addAll(membersVO);
        return results;

    }
}
