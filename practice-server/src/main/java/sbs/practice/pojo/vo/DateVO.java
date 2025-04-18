package sbs.practice.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.DateInfo;

@ApiModel(description = "向老师显示负责人打卡情况姓名和电话")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateVO {
    @ApiModelProperty("项目id")
    private Integer projectId;
    @ApiModelProperty("负责人学号")
    private String leaderId;
    @ApiModelProperty("负责人名字")
    private String leaderName;
    @ApiModelProperty("负责人手机号")
    private String leaderPhone;
    @ApiModelProperty("打卡状态")
    private DateInfo info;
    @ApiModelProperty("打卡时间")
    private String date;
    @ApiModelProperty("打卡开始时间")
    private String startTime;
    @ApiModelProperty("打卡结束时间")
    private String endTime;
}
