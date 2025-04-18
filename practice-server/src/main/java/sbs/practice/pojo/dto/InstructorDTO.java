package sbs.practice.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.InstructorTitleEnum;
import sbs.practice.pojo.entity.Instructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorDTO implements Serializable {

    private Integer projectId;
    @ApiModelProperty("姓名")
    private String name;
    private String phone;
    @ApiModelProperty("老师所属部门")
    private String departName;
    @ApiModelProperty("指导老师职称")
    private InstructorTitleEnum title;


}
