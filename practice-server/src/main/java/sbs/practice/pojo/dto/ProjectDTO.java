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
@ApiModel(description = "负责人立项数据")
public class ProjectDTO implements Serializable {
    @ApiModelProperty("项目主题id")
    public Integer subjectId;
    @ApiModelProperty("负责人电话")
    private String leaderPhone;
    @ApiModelProperty("项目标题")
    private String title;
}
