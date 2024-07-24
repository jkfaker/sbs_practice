package sbs.practice.service.impl;

import lombok.extern.slf4j.Slf4j;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.UserType;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.SecTeacher;
import sbs.practice.mapper.SecTeacherMapper;
import sbs.practice.service.ISecTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
