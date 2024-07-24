package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.DateInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "接收打卡")
public class DateDTO {
    @ApiModelProperty("打卡日期")
    private String date;
    @ApiModelProperty("打卡描述")
    private DateInfo info;
}
