package sbs.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.UserType;
import sbs.practice.common.exception.DeleteException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.UpdateException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.SecTeacherMapper;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.SecTeacher;
import sbs.practice.pojo.vo.TeacherInfoVO;
import sbs.practice.service.ISecTeacherService;

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
public class SecTeacherServiceImpl extends ServiceImpl<SecTeacherMapper, SecTeacher> implements ISecTeacherService {

    @Override
    public UserType login() {
        UserDTO user = BaseContext.getCurrentUser();
        try {
            SecTeacher secTeacher = getById(user.getCampusId());
            log.info(secTeacher.getId());
            return UserType.TEACHER;
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return UserType.STUDENT;
        }
    }


    @Override
    public void add(SecTeacher secTeacher) {
        TokenUtils.verifyTeacher();
        if (!this.save(secTeacher)){
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }

    }

    @Override
    public void delete(SecTeacher secTeacher) {
        TokenUtils.verifyTeacher();
        if (!this.removeById(secTeacher)){
            throw new DeleteException(MessageConstant.DELETE_FAILED);
        }
    }

    @Override
    public void renew(SecTeacher secTeacher) {
        TokenUtils.verifyTeacher();
        if(!this.saveOrUpdate(secTeacher)){
            throw new UpdateException(MessageConstant.UPDATE_FAILED);
        }
    }

    /**
     * 获取当前教师工号
     * @return 教师工号
     */
    @Override
    public String getTeacherId() {
        TokenUtils.verifyTeacher();
        return BaseContext.getCurrentUser().getCampusId();
    }

    /**
     * 查询当前老师的departId
     */
    @Override
    public Integer findDepartId() {
        UserDTO user = BaseContext.getCurrentUser();
        TokenUtils.verifyTeacher();
        SecTeacher secTeacher = this.getById(user.getCampusId());
        return secTeacher.getDepartId();
    }


    /**
     * 通过teacherId获取老师姓名
     * @return TeacherName
     */
    @Override
    public String getTeacherName(String teacherId) {
        SecTeacher secTeacher = this.getById(teacherId);
        return secTeacher.getName();
    }

    /**
     * 获取教师工号 姓名、部门
     * @return
     */
    @Override
    public TeacherInfoVO selectIdAndName() {
        TeacherInfoVO teacherInfoVO = new TeacherInfoVO();
        String teacherId = getTeacherId();
        String teacherName = getTeacherName(teacherId);
        teacherInfoVO.setTeacherId(teacherId);
        teacherInfoVO.setTeacherName(teacherName);
        return teacherInfoVO;
    }
}
