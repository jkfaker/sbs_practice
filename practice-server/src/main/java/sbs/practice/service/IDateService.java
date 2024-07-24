package sbs.practice.service;

import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
