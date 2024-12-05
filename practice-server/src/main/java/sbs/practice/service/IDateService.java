package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.pojo.vo.DateVO;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */
public interface IDateService extends IService<Date> {

    void clockIn(DateDTO dateDTO);

    List<Date> show();

    List<DateVO> teacher(LocalDate date, Integer subjectId);
}
