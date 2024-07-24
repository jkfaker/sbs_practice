package sbs.practice.pojo.vo;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.DepartEnum;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "返回负责人信息")
public class LeaderVO implements Serializable {
    @ApiModelProperty("负责人学号")
    private String leaderId;
    @ApiModelProperty("负责人姓名")
    private String leaderName;
    // 返回departName
    @ApiModelProperty("负责人所在学院")
    private DepartEnum leaderDepart;
}
