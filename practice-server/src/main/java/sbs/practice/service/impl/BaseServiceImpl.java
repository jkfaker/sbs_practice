package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.exception.SelectException;
import sbs.practice.mapper.ProjectMapper;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Project;
import sbs.practice.service.IBaseService;


@Service
public class BaseServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IBaseService {
    /**
     * 通过leaderId获取projectId
     * 要求：项目抛出异常
     * @param leaderId
     * @return projectId
     */
    @Override
    public Integer getProjectId(String leaderId) {
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, leaderId);
        Project project = this.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        } catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        return projectId;
    }

    /**
     * 通过当前用户获取projectId
     * @return
     */
    @Override
    public Integer getProjectIdByCurrentUser() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = this.getOne(projectWrapper);
        Integer projectId;
        try {
            projectId = project.getId();
        } catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        return projectId;
    }

}
