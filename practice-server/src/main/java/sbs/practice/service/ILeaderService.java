package sbs.practice.service;

import sbs.practice.pojo.vo.LeaderVO;

public interface ILeaderService {
    LeaderVO selectIdAndName();

    String getUserId();
}
