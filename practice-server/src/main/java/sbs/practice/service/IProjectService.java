package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import sbs.practice.common.enums.DateInfo;
import sbs.practice.common.enums.JudgeExist;
import sbs.practice.pojo.dto.InstructorDTO;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.dto.MemberDTO;
import sbs.practice.pojo.dto.ProjectDTO;
import sbs.practice.pojo.entity.Project;
import sbs.practice.pojo.vo.ProjectAndFileVO;
import sbs.practice.pojo.vo.ProjectVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface IProjectService extends IService<Project> {

    void upload(ProjectDTO project, List<MemberDTO> members, List<InstructorDTO> instructorsDTO, MultipartFile file);

    List<Project> getAllInDepart(Integer subjectId);

    void labelUpdate(LabelDTO labelDTO);

    ProjectVO getName();

    JudgeExist judge();

    List<Project> findByDepartId(Integer departId);

    List<ProjectAndFileVO> getMidTerm(List<Project> projects, Integer fileType);

    List<ProjectAndFileVO> getFiles(Integer subjectId, Integer fileType, String projectName);

    boolean isSchoolLabel(Integer projectId);

    Project getByProjectId(Integer projectId);

    void updateDateStatus(Integer projectId, String date, DateInfo dateInfo);
}
