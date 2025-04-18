package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "消息DTO")
public class AnnounceDTO implements Serializable {
    @ApiModelProperty("接收项目的id")
    private Integer projectId;
    @ApiModelProperty("发送的消息内容")
    private String text;
    @ApiModelProperty("消息标签")
    private Integer label;
}

