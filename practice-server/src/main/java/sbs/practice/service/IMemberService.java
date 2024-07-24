package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.entity.Member;
import sbs.practice.pojo.vo.MemberVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface IMemberService extends IService<Member> {

    List<MemberVO> getByLeader();

}
