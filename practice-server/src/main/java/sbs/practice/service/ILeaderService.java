package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.vo.LeaderVO;

public interface ILeaderService {
    LeaderVO selectIdAndName();
}
