package sbs.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.mapper.DateMapper;
import sbs.practice.pojo.dto.DateDTO;
import sbs.practice.pojo.entity.Date;
import sbs.practice.pojo.vo.DateVO;
import sbs.practice.service.IBaseService;
import sbs.practice.service.IDateService;
import sbs.practice.service.IProjectService;
import sbs.practice.service.ISecTeacherService;

import java.time.LocalDate;
import java.util.List;

/**
 *  服务实现类
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DateServiceImpl extends ServiceImpl<DateMapper, Date> implements IDateService {

    /**
     * 要求：
     * 1，date表，查找相同数据，避免反复打卡
     * 2，非负责人打卡报错
     * @param dateDTO
     */

    private final ISecTeacherService secTeacherService;
    private final IBaseService baseService;
    private final DateMapper dateMapper;




    @Override
    public void clockIn(DateDTO dateDTO) {
        // 查询和验证 projectId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        // copy属性至Date类中
        Date date = new Date();
        BeanUtil.copyProperties(dateDTO,date);
        date.setProjectId(projectId);
        // 查找相同数据，避免多次打卡
        LambdaQueryWrapper<Date> dateWrapper = new QueryWrapper<Date>()
                .lambda()
                .eq(Date::getDate, date.getDate())
                .eq(Date::getProjectId, date.getProjectId())
                .eq(Date::getInfo, date.getInfo());
        Date dateConflict = this.getOne(dateWrapper);
        if (dateConflict != null) {
            // 如果找到了重复记录，则直接抛出异常，无需再调用getId()引发不必要的错误处理
            throw new SelectException(MessageConstant.DATE_CONFLICT_ERROR);
        }
        log.info("插入date数据:{}",date);
        if (!save(date))
            throw new InsertDatabaseException(MessageConstant.SELECT_FAILED);
    }

    /**
     * 负责人 显示所有打卡日期
     * 要求：
     * 非负责人报错
     */
    @Override
    public List<Date> show(Integer projectId) {


        LambdaQueryWrapper<Date> dateWrapper = new QueryWrapper<Date>()
                .lambda()
                .eq(Date::getProjectId, projectId);
        return list(dateWrapper);
    }

    /**
     * 要求：
     * @param date
     * @param subjectId
     * @return
     */
    @Override
    public List<DateVO> teacher(LocalDate date, Integer subjectId) {
        Integer departId = secTeacherService.findDepartId();
        // 联合查询： project + date，其中info check(0,1)
        log.info("departId:{}", departId);
        List<DateVO> dateVOList = dateMapper.teacher(date, subjectId, departId);
        return dateVOList;
    }
}
