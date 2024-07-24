package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;

import lombok.extern.slf4j.Slf4j;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.mapper.DateMapper;
import sbs.practice.pojo.entity.Project;
import sbs.practice.service.IDateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *  服务实现类
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */
@Service
@Slf4j
public class DateServiceImpl extends ServiceImpl<DateMapper, Date> implements IDateService {

    /**
     * 要求：
     * 1，date表，查找相同数据，避免反复打卡
     * 2，非负责人打卡报错
     * @param dateDTO
     */

    @Override
    public void clockIn(DateDTO dateDTO) {
        // 根据token查campusId
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 查询project，根据负责人获得projectId，
        LambdaQueryWrapper<Project> wrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(wrapper);
        Integer projectId;
        // 如果非项目负责人打卡
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        // copy属性至Date类中
        Date date = new Date();
        BeanUtil.copyProperties(dateDTO,date);
        date.setProjectId(projectId);
        // 查找相同数据，避免多次打卡
        LambdaQueryWrapper<Date> dateWrapper = new QueryWrapper<Date>()
                .lambda()
                .eq(Date::getDate, date.getDate())
                .eq(Date::getProjectId, date.getProjectId())
                .eq(Date::getInfo, date.getInfo());
        Date dateConflict = this.getOne(dateWrapper);
        if (dateConflict != null) {
            // 如果找到了重复记录，则直接抛出异常，无需再调用getId()引发不必要的错误处理
            throw new SelectException(MessageConstant.DATE_CONFLICT_ERROR);
        }
        log.info("插入date数据:{}",date);
        // 插入至date
        boolean save;
        try {
            save = save(date);
        }catch (Exception e) {
            throw new InsertDatabaseException(MessageConstant.SELECT_FAILED);
        }
        if (!save)
            throw new InsertDatabaseException(MessageConstant.SELECT_FAILED);
    }

    /**
     * 要求：
     * 非负责人报错
     * @return
     */
    @Override
    public List<Date> show() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        LambdaQueryWrapper<Project> wrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(wrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        LambdaQueryWrapper<Date> dateWrapper = new QueryWrapper<Date>()
                .lambda()
                .eq(Date::getProjectId, projectId);
        return list(dateWrapper);
    }

}
