package sbs.practice.service;

import sbs.practice.pojo.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

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
