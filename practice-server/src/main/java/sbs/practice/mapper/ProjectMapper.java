package sbs.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.ProjectAndFileVO;

import java.util.List;

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

    List<ProjectAndFileVO> selectFiles(Integer subjectId ,Integer fileType, Integer departId);
}
