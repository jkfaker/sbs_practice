package sbs.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.DeleteException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.UpdateException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.mapper.SubjectMapper;
import sbs.practice.pojo.entity.Subject;
import sbs.practice.service.ISubjectService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-17
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Override
    public void add(Subject subject) {
        TokenUtils.verifyTeacher();
        if (!this.save(subject)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
    }

    @Override
    public void delete(Subject subject) {
        TokenUtils.verifyTeacher();
        if (!this.removeById(subject)) {
            throw new DeleteException(MessageConstant.DELETE_FAILED);
        }
    }

    @Override
    public void renew(Subject subject) {
        TokenUtils.verifyTeacher();
        this.saveOrUpdate(subject);
        if (!this.saveOrUpdate(subject)) {
            throw new UpdateException(MessageConstant.UPDATE_FAILED);

        }
    }

}


