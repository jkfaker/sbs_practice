package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "调用今日校园消息发送接口时获取的AccessToken")
public class MessageAccessTokenDTO implements Serializable {
    @ApiModelProperty("错误码")
    private String errcode;
    private String errmsg;
    @ApiModelProperty("内容即为accessToken")
    private String data;
}
