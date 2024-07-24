package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.enums.JudgeExist;
import sbs.practice.common.exception.AuthenticationException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.common.utils.FileIO;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.dto.MemberDTO;
import sbs.practice.pojo.dto.ProjectDTO;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.*;
import sbs.practice.mapper.ProjectMapper;
import sbs.practice.pojo.vo.ProjectVO;
import sbs.practice.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static sbs.practice.common.utils.EnumStringToValue.getEnumByValue;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    /**
     * 要求：
     * 1，
     * @param membersDTO
     * @param projectDTO
     * @param file
     */
    @Override
    @Transactional
    public void upload(List<MemberDTO> membersDTO, ProjectDTO projectDTO, MultipartFile file) {

        // 获取负责人数据
        UserDTO user = BaseContext.getCurrentUser();

        DepartEnum depart = DepartEnum.getDepartByName(user.getUserDepart());


        // 1, insert project 数据库
        Project project = Project.builder()
                .departId(depart)
                .subjectId(projectDTO.getSubjectId())
                .title(projectDTO.getTitle())
                .leaderId(user.getCampusId())
                .leaderName(user.getUserName())
                .leaderClass(user.getUserClass())
                .leaderPhone(projectDTO.getLeaderPhone())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        log.info("project:{}",project);
        boolean result1 = save(project);
        if (!result1)
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        Integer projectId = project.getId();

        // 3, insert member
        // 为每一个项目成员填一个projectId
        for (MemberDTO memberDTO : membersDTO) {
            Member member = new Member();
            member.setProjectId(projectId);
            BeanUtil.copyProperties(memberDTO, member);
            member.setCreateTime(LocalDateTime.now());
            boolean result3 = Db.save(member);
            if (!result3)
                throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }

        // 文件上传到本地
        String fileName = FileIO.uploadFile(file);
        // 2, insert file
        Files files = Files.builder()
                .projectId(projectId)
                .type(FileType.START)
                .fileName(fileName)
                .uploadTime(LocalDateTime.now())
                .build();
        log.info("files: {}", files);
        boolean result2 = Db.save(files);
        if (!result2)
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
    }

    /**
     * 老师获得该学院所有项目信息
     * @return
     */
    @Override
    public List<Project> selectByDepart() {
        // 当前老师
        UserDTO userDTO = BaseContext.getCurrentUser();
        // 拿到当前老师工号
        String userId = userDTO.getCampusId();
        // 数据库查找对应学院
        SecTeacher teacher = Db.getById(userId, SecTeacher.class);
        Integer departId = teacher.getDepartId();
        // 数据库查找对应学院所有项目
        QueryWrapper<Project> queryWrapper = new QueryWrapper<Project>();
        List<Project> projects = list(queryWrapper
                .lambda()
                .eq(Project::getDepartId, departId));
        return projects;
    }

    /**
     * 同时验证老师学院id，避免漏洞
     * @param labelDTO
     */
    @Override
    public void labelUpdate(LabelDTO labelDTO) {
        UpdateWrapper<Project> updateWrapper = new UpdateWrapper<Project>();
        // 得到老师工号
        String teacherId = BaseContext.getCurrentUser().getCampusId();
        // 查询老师学院
        SecTeacher teacher = Db.getById(teacherId, SecTeacher.class);
        // 比对DepartId,如果不是抛出错误
        if (labelDTO.getDepartId().getDepartId() != teacher.getDepartId())
            throw new AuthenticationException(MessageConstant.AUTHENTICATION_FAILED);
        updateWrapper.lambda()
                .eq(Project::getId, labelDTO.getId())
                .set(Project::getLabel, labelDTO.getLabel());
        update(updateWrapper);
        return;
    }

    @Override
    public ProjectVO getName() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 获取project
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        String projectName;
        Integer subjectId;
        try {
            subjectId = project.getSubjectId();
            projectName = project.getTitle();
        }catch (RuntimeException e) {
            throw new SelectException(MessageConstant.PROJECT_NOT_EXIST);
        }
        Subject subject = Db.getById(subjectId, Subject.class);
        String subjectName = subject.getName();
        // 构造projectVO
        ProjectVO result = ProjectVO
                .builder()
                .projectName(projectName)
                .subjectName(subjectName)
                .build();
        return result;
    }

    @Override
    public JudgeExist judge() {
        UserDTO user = BaseContext.getCurrentUser();
        String userId = user.getCampusId();
        // 获取project
        LambdaQueryWrapper<Project> projectWrapper = Wrappers.lambdaQuery(Project.class)
                .eq(Project::getLeaderId, userId);
        Project project = Db.getOne(projectWrapper);
        // 判断project是否为空
        if (project.getId() == null || project.getId().toString().isEmpty())
            return JudgeExist.NOT_EXIST;
        else
            return JudgeExist.EXIST;
    }
}
