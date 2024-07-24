package sbs.practice.pojo.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "负责人输入项目成员")
public class MemberDTO implements Serializable {
    @ApiModelProperty("项目成员学号")
    private String memberId;
    @ApiModelProperty("项目成员姓名")
    private String memberName;
    @ApiModelProperty("项目成员联系方式")
    private String memberPhone;
}
