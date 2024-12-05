package sbs.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import sbs.practice.pojo.entity.Date;
import sbs.practice.pojo.vo.DateVO;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */
@Mapper
public interface DateMapper extends BaseMapper<Date> {

    List<DateVO> teacher(LocalDate date, Integer subjectId, Integer departId);
}
