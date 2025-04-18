package sbs.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import sbs.practice.common.constant.AnnounceReadConstant;
import sbs.practice.common.constant.AnnounceTypeConstant;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.AuthenticationException;
import sbs.practice.common.exception.InsertDatabaseException;
import sbs.practice.common.exception.SelectException;
import sbs.practice.mapper.AnnounceMapper;
import sbs.practice.pojo.dto.AnnounceDTO;
import sbs.practice.pojo.entity.Announce;
import sbs.practice.pojo.vo.AnnounceVO;
import sbs.practice.service.IAnnounceService;
import sbs.practice.service.IBaseService;
import sbs.practice.service.ISecTeacherService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-01-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AnnounceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements IAnnounceService {

    private final IBaseService baseService;
    private final ISecTeacherService secTeacherService;
    private final AnnounceMapper announceMapper;

    /**
     * 统计当前学生未读消息总数
     * 要求： 1， 当前学生
     * 2，未读消息
     *
     * @return 消息总数
     */
    @Override
    public Long countUnreadAnnounceOfUser() {
        // project 不存在不报错
        Integer projectId;
        try {
            projectId = baseService.getProjectIdByCurrentUser();
        } catch (SelectException e) {
            return 0L;
        }
        LambdaQueryWrapper<Announce> queryWrapper = new QueryWrapper<Announce>()
                .lambda()
                .eq(Announce::getProjectId, projectId)
                .eq(Announce::getIsRead, AnnounceReadConstant.ANNOUNCE_UNREAD);
        long count = this.count(queryWrapper);
        log.info("count:{}", count);
        return count;
    }


    /**
     * @return 消息列表
     * 学生 获得发给自己所有消息
     * 要求： 1，当前学生
     * 2，时间倒序排列
     */
    @Override
    public List<AnnounceVO> getAllAnnounceOfUser() {
        Integer projectId;
        try {
            projectId = baseService.getProjectIdByCurrentUser();
        } catch (SelectException e) {
            return new ArrayList<AnnounceVO>();
        }
        LambdaQueryWrapper<Announce> queryWrapper = new QueryWrapper<Announce>()
                .lambda()
                .eq(Announce::getProjectId, projectId)
                .orderByDesc(Announce::getCreateTime);
        List<Announce> announceList = this.list(queryWrapper);
        // announceVO列表
        List<AnnounceVO> announceVOList = new ArrayList<AnnounceVO>();
        for (Announce announce : announceList) {
            AnnounceVO announceVO = new AnnounceVO();
            BeanUtils.copyProperties(announce, announceVO);
            // 如果非系统消息 获取老师姓名
            if (announce.getMessageType() == AnnounceTypeConstant.TEACHER_MESSAGE) {
                String teacherName = secTeacherService.getTeacherName(announce.getTeacherId());
                announceVO.setTeacherName(teacherName);
            }
            announceVOList.add(announceVO);
        }
        return announceVOList;
    }


    /**
     * 老师 向学生发送消息
     *
     * @param announceDTO :porjectId text
     *                    要求：1，插入数据失败报错
     */
    @Override
    public void sentMessageToStudent(AnnounceDTO announceDTO) {

        String teacherId = secTeacherService.getTeacherId();
        LocalDateTime now = LocalDateTime.now();
        Boolean isRead = AnnounceReadConstant.ANNOUNCE_UNREAD;
        // 消息类型
        Short type = AnnounceTypeConstant.TEACHER_MESSAGE;
        Announce announce = Announce.builder()
                .projectId(announceDTO.getProjectId())
                .text(announceDTO.getText())
                .messageType(type)
                .teacherId(teacherId)
                .isRead(isRead)
                .createTime(now)
                .updateTime(now)
                .build();
        if (!this.save(announce)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        return;
    }


    /**
     * 系统 向学生发送消息
     *
     * @param announceDTO :porjectId text
     *                    要求：1，插入数据失败报错
     */
    @Override
    public void sentSystemMessage(AnnounceDTO announceDTO) {

        LocalDateTime now = LocalDateTime.now();
        Boolean isRead = AnnounceReadConstant.ANNOUNCE_UNREAD;
        Short type = AnnounceTypeConstant.SYSTEM_MESSAGE;

        Announce announce = Announce.builder()
                .projectId(announceDTO.getProjectId())
                .text(announceDTO.getText())
                .messageType(type)
                .isRead(isRead)
                .label(announceDTO.getLabel())
                .createTime(now)
                .updateTime(now)
                .build();
        if (!this.save(announce)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        return;
    }


    /**
     * 学生点击全部已读
     */
    @Override
    public void studentAllRead() {
        // 获取projctId
        Integer projectId = baseService.getProjectIdByCurrentUser();
        Boolean isRead = AnnounceReadConstant.ANNOUNCE_UNREAD;
        // updateWrapper update read where projectId and unread
        LambdaUpdateWrapper<Announce> updateWrapper = new UpdateWrapper<Announce>()
                .lambda()
                .set(Announce::getIsRead, AnnounceReadConstant.ANNOUNCE_READ)
                .eq(Announce::getProjectId, projectId)
                .eq(Announce::getIsRead, AnnounceReadConstant.ANNOUNCE_UNREAD);
        // 如果更新错误
        if (!this.update(updateWrapper)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        ;
        return;
    }

    /**
     * 学生已读当前消息
     * 要求：
     * 1，announce 的 projectId = current project
     *
     * @param id
     */
    @Override
    public void studentOneRead(Integer id) {
        // 如果不是当前project的announce 报错
        if (this.getById(id).getProjectId() != baseService.getProjectIdByCurrentUser()) {
            throw new AuthenticationException(MessageConstant.AUTHENTICATION_FAILED);
        }
        ;

        LambdaUpdateWrapper<Announce> updateWrapper = new UpdateWrapper<Announce>()
                .lambda()
                .set(Announce::getIsRead, AnnounceReadConstant.ANNOUNCE_READ)
                .eq(Announce::getId, id);

        // 如果更新错误
        if (!this.update(updateWrapper)) {
            throw new InsertDatabaseException(MessageConstant.INSERT_DATABASE_FAILED);
        }
        ;
        return;
    }


    /**
     * 老师获取发给学生的所有消息记录
     *
     * @param projectId
     * @return
     */
    @Override
    public List<AnnounceVO> getTeacherAnnounce(Integer projectId) {
        LambdaQueryWrapper<Announce> queryWrapper = new QueryWrapper<Announce>()
                .lambda()
                .eq(Announce::getProjectId, projectId)
                .eq(Announce::getMessageType, AnnounceTypeConstant.TEACHER_MESSAGE)
                .orderByDesc(Announce::getCreateTime);
        List<Announce> announceList = this.list(queryWrapper);
        // announceVO列表
        List<AnnounceVO> announceVOList = new ArrayList<AnnounceVO>();
        for (Announce announce : announceList) {
            AnnounceVO announceVO = new AnnounceVO();
            BeanUtils.copyProperties(announce, announceVO);
            // 如果非系统消息 获取老师姓名
            if (announce.getMessageType() == AnnounceTypeConstant.TEACHER_MESSAGE) {
                String teacherName = secTeacherService.getTeacherName(announce.getTeacherId());
                announceVO.setTeacherName(teacherName);
            }
            announceVOList.add(announceVO);
        }
        return announceVOList;
    }
}
