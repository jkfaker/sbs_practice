package sbs.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Files;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */

@Mapper
public interface FilesMapper extends BaseMapper<Files> {

    List<String> selectFileName(Integer departId, Integer subjectId, Integer fileType);
}
