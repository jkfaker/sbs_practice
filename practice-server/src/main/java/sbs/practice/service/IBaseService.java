package sbs.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sbs.practice.pojo.entity.Project;

public interface IBaseService extends IService<Project> {

    Integer getProjectId(String leaderId);

    Integer getProjectIdByCurrentUser();
}
