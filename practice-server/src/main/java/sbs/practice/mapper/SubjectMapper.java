package sbs.practice.mapper;

import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-17
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

}
