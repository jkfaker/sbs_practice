package sbs.practice.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.vo.LeaderVO;
import sbs.practice.service.ILeaderService;

@Service
@Slf4j
public class LeaderServiceImpl implements ILeaderService  {

    @Override
    public LeaderVO selectIdAndName() {
        UserDTO userDTO = BaseContext.getCurrentUser();
        log.info("所属部门：{}",userDTO.getUserDepart());
        DepartEnum depart = DepartEnum.getDepartByName(userDTO.getUserDepart());

        LeaderVO leaderVO = LeaderVO.builder()
                .leaderId(userDTO.getCampusId())
                .leaderName(userDTO.getUserName())
                .leaderDepart(depart)
                .build();
        return leaderVO;
    }

    /**
     * 获取当前用户id
     * @return
     */
    @Override
    public String getUserId() {
        return BaseContext.getCurrentUser().getCampusId();
    }
}
