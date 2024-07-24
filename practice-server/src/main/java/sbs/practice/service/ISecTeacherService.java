package sbs.practice.service;

import org.apache.catalina.User;
import sbs.practice.common.enums.UserType;
import sbs.practice.pojo.entity.SecTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
public interface ISecTeacherService extends IService<SecTeacher> {

    UserType login();
}
