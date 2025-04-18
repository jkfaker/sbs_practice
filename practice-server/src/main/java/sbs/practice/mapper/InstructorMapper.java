package sbs.practice.mapper;

import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Instructor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 指导老师 Mapper 接口
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-04-16
 */
@Mapper
public interface InstructorMapper extends BaseMapper<Instructor> {

}
