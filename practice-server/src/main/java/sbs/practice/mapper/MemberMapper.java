package sbs.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Member;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}