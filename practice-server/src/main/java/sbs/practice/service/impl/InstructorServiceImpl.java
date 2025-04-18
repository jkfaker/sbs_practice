package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.pojo.dto.InstructorDTO;
import sbs.practice.pojo.entity.Instructor;
import sbs.practice.mapper.InstructorMapper;
import sbs.practice.service.IInstructorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 指导老师 服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-04-16
 */
@Service
public class InstructorServiceImpl extends ServiceImpl<InstructorMapper, Instructor> implements IInstructorService {
    @Override
    public void insertInstructorList(Integer projectId, List<InstructorDTO> instructorsDTO) {
        List<Instructor> instructors = new ArrayList<Instructor>();
        for (InstructorDTO instructorDTO : instructorsDTO) {
            Instructor instructor = new Instructor();
            BeanUtil.copyProperties(instructorDTO, instructor);
            instructor.setProjectId(projectId);
            instructor.setCreateTime(LocalDateTime.now());
            instructors.add(instructor);
        }
        boolean result = this.saveBatch(instructors);
        if (!result) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
    }


    /**
     * 根据项目id查询指导老师s
     * @param projectId
     * @return
     */
    @Override
    public List<Instructor> getListByProjectId(Integer projectId) {
        LambdaQueryWrapper<Instructor> queryWrapper = new QueryWrapper<Instructor>()
                .lambda()
                .eq(Instructor::getProjectId, projectId);
        List<Instructor> instructors = this.list(queryWrapper);
        return instructors;
    }
}


