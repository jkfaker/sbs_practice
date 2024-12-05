package sbs.practice.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "从API获取到的数据并封装成user对象")
public class UserDTO implements Serializable {
    @ApiModelProperty("今日校园用户id")
    private String campusUserId;
    @ApiModelProperty("学号或工号")
    private String campusId;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户性别， 0为女，1为男")
    private String gender;
    @ApiModelProperty("用户类型")
    private String userType;
    @ApiModelProperty("用户住址编号")
    private String tenantCode;
    @ApiModelProperty("用户住址名称")
    private String tenantName;
    @ApiModelProperty("用户头像")
    private String userAvatar;
    @ApiModelProperty("用户所属学院")
    private String userDepart;
    @ApiModelProperty("用户专业")
    private String userMajor;
    @ApiModelProperty("用户所在年级")
    private String userGrade;
    @ApiModelProperty("用户所在班级")
    private String userClass;
    @ApiModelProperty("用户背景图片")
    private String userBackgroundImg;
}
