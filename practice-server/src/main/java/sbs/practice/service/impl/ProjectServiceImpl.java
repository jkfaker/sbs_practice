package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.enums.JudgeExist;
import sbs.practice.common.enums.ProjectLabel;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.NotExistException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.ProjectMapper;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.dto.MemberDTO;
import sbs.practice.pojo.dto.ProjectDTO;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.Files;
import sbs.practice.pojo.entity.Member;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.entity.Subject;
import sbs.practice.pojo.vo.ProjectAndFileVO;
import sbs.practice.pojo.vo.ProjectVO;
import sbs.practice.service.IProjectService;
import sbs.practice.service.ISecTeacherService;

import java.time.LocalDateTime;
import java.util.List;

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
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    private final ProjectMapper projectMapper;
    private final FilesServiceImpl filesService;
    private final ISecTeacherService secTeacherService;

    /**
     * 组长立项上传
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
        // 上传文件
        filesService.uploadFile(file, FileType.START);
    }

    /**
     * 老师获得该学院所有项目信息
     * @return
     */
    @Override
    public List<Project> getAllInDepart(Integer subjectId) {
        Integer departId = secTeacherService.findDepartId();
        // 数据库查找对应学院所有项目
        LambdaQueryWrapper<Project> queryWrapper = new QueryWrapper<Project>()
                .lambda()
                .eq(Project::getDepartId, departId);
        if (subjectId != null) {
            queryWrapper.eq(Project::getSubjectId, subjectId);
        }
        List<Project> projects = list(queryWrapper);
        return projects;
    }

    /**
     * @param labelDTO
     */
    @Override
    public void labelUpdate(LabelDTO labelDTO) {
        TokenUtils.verifyTeacher();
        UpdateWrapper<Project> updateWrapper = new UpdateWrapper<Project>();
        updateWrapper.lambda()
                .eq(Project::getId, labelDTO.getId())
                .set(Project::getLabel, labelDTO.getLabel());
        this.update(updateWrapper);
        return;
    }

    /**
     * 获取当前用户作为项目负责人所负责的项目名称
     * @return ProjectVO 包含项目名称和学科名称的对象
     * @throws SelectException 如果找不到项目，则抛出选择异常
     */
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
        if (project == null || project.getId() == null || project.getId().toString().isEmpty())
            return JudgeExist.NOT_EXIST;
        else
            return JudgeExist.EXIST;
    }

    /**
     * 从project中departId
     * @param departId
     * @return
     */

    @Override
    public List<Project> findByDepartId(Integer departId) {
        LambdaQueryWrapper<Project> projectQueryWrapper = new QueryWrapper<Project>()
                .lambda()
                .eq(Project::getDepartId, departId);
        return this.list(projectQueryWrapper);
    }

    @Override
    public List<ProjectAndFileVO> getMidTerm(List<Project> projects, Integer fileType) {
           return projects.stream()
                .map(((project) -> {
                    LambdaQueryWrapper<Files> filesWrapper = Wrappers.lambdaQuery(Files.class)
                            .eq(Files::getProjectId, project.getId())
                            .eq(Files::getType, fileType);
                    Files files = Db.getOne(filesWrapper);
                    ProjectAndFileVO projectAndFileVO = new ProjectAndFileVO();
                    // 将project 属性 copy to projectAndFileVO
                    BeanUtil.copyProperties(project, projectAndFileVO);
                    if (files != null) {
                        projectAndFileVO.setType(files.getType());
                        projectAndFileVO.setUploadTime(files.getUploadTime());
                        projectAndFileVO.setFileLabel(files.getLabel());
                        projectAndFileVO.setExamineTime(files.getExamineTime());
                    }
                    return projectAndFileVO;
                })).toList();
    }

    @Override
    public List<ProjectAndFileVO> getFiles(Integer subjectId, Integer fileType) {
        // 查找教师学院
        Integer departId = secTeacherService.findDepartId();
        return projectMapper.selectFiles(subjectId, fileType, departId);
    }

    /**
     * 判断项目是否属于校级立项
     * 是返回1，否返回0
     */
    @Override
    public boolean isSchoolLabel(Integer projectId) {
        Project project = this.getById(projectId);
        return project.getLabel() == ProjectLabel.SCHOOL;
    }

    /**
     * 老师查询单个的项目
     * @param projectId
     * @return
     */
    @Override
    public Project getByProjectId(Integer projectId) {
        TokenUtils.verifyTeacher();
        Project project = this.getById(projectId);
        return project;
    }
}
