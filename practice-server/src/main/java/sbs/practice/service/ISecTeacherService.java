package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.common.enums.UserType;
import sbs.practice.pojo.entity.SecTeacher;
import sbs.practice.pojo.vo.TeacherInfoVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface ISecTeacherService extends IService<SecTeacher> {

    UserType login();

    Integer findDepartId();

    void add(SecTeacher secTeacher);

    void delete(SecTeacher secTeacher);

    void renew(SecTeacher secTeacher);

    String getTeacherId();

    String getTeacherName(String teacherId);

    TeacherInfoVO selectIdAndName();
}
