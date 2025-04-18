package sbs.practice.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.ProjectLabel;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "负责人回显项目名称与主题")
public class ProjectVO implements Serializable {
    @ApiModelProperty("项目主题名称")
    private String subjectName;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目标签")
    private ProjectLabel label;
}
