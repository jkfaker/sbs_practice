package sbs.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.News;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

}
