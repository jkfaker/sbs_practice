package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbs.practice.common.context.BaseContext;
import sbs.practice.mapper.MemberMapper;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Member;
import sbs.practice.pojo.vo.MemberVO;
import sbs.practice.service.IBaseService;
import sbs.practice.service.IMemberService;
import sbs.practice.service.IProjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    private final IBaseService baseService;
    /**
     * 负责人 获取队伍成员信息
     * @return
     */
    @Override
    public List<MemberVO> getByLeader() {
        UserDTO user = BaseContext.getCurrentUser();
        Integer projectId = baseService.getProjectIdByCurrentUser();

        // 根据projectId查找成员
        LambdaQueryWrapper<Member> memberWrapper = Wrappers.lambdaQuery(Member.class)
                .eq(Member::getProjectId, projectId);
        List<Member> members = Db.list(memberWrapper);

        // 先添加负责人信息，把负责人放在第一个
        MemberVO leader = new MemberVO(user.getCampusId(), user.getUserName());
        List<MemberVO> results = new ArrayList<>();
        results.add(leader);
        // 通过steam流添加其他队员
        List<MemberVO> membersVO = members.stream()
                .map(member -> new MemberVO(member.getMemberId(), member.getMemberName()))
                .toList();
        results.addAll(membersVO);
        return results;

    }
}
