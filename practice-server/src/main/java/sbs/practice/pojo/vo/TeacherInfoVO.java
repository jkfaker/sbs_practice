package sbs.practice.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.DepartEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "老师信息")
public class TeacherInfoVO {
    @ApiModelProperty("老师工号")
    private String teacherId;
    @ApiModelProperty("老师姓名")
    private String teacherName;
    // 返回departName
    @ApiModelProperty("老师部门")
    private DepartEnum teacherDepart;
}
