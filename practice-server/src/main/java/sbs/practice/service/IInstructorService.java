package sbs.practice.service;

import cn.hutool.core.bean.BeanUtil;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.pojo.dto.InstructorDTO;
import sbs.practice.pojo.entity.Instructor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 指导老师 服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-04-16
 */
public interface IInstructorService extends IService<Instructor> {

    void insertInstructorList(Integer projectId, List<InstructorDTO> instructorsDTO);

    List<Instructor> getListByProjectId(Integer projectId);
}
