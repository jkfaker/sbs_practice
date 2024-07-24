package sbs.practice.mapper;

import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuQiDuo
 * @since 2024-06-26
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

}
