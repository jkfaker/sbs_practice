package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "调用今日校园消息发送接口response")
public class SendMessageDTO {
    // 错误时显示
    @ApiModelProperty("错误码")
    private String errcode;
    @ApiModelProperty("错误消息")
    private String errmsg;
    // 成功时显示
    @ApiModelProperty("信息发送状态")
    private String msg;
    @ApiModelProperty("消息id")
    private String msgId;
    private Integer status;

}
