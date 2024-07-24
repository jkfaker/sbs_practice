package sbs.practice.service.impl;

import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.exception.UpdateException;
import sbs.practice.common.utils.TokenUtils;
import sbs.practice.pojo.entity.Subject;
import sbs.practice.mapper.SubjectMapper;
import sbs.practice.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
        this.save(subject);
    }

    @Override
    public void delete(Subject subject) {
        TokenUtils.verifyTeacher();
        this.removeById(subject);
    }

    @Override
    public void renew(Subject subject) {
        TokenUtils.verifyTeacher();
        boolean isUpdate = this.updateById(subject);
        if (!isUpdate)
            throw new UpdateException(MessageConstant.UPDATE_FAILED);
    }
}


