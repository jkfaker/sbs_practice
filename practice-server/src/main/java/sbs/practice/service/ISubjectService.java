package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.entity.Subject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-17
 */
public interface ISubjectService extends IService<Subject> {

    void add(Subject subject);

    void delete(Subject subject);

    void renew(Subject subject);

}
