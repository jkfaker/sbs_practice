package sbs.practice.pojo.vo;

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
@ApiModel(description = "参与项目的成员")
public class MemberVO implements Serializable {
    @ApiModelProperty("成员id")
    private String memberId;
    @ApiModelProperty("成员名称")
    private String memberName;
}