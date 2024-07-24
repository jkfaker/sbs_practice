package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.common.enums.ProjectLabel;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("project")
@ApiModel(value="项目", description="")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("负责人学号")
    private String leaderId;
    @ApiModelProperty("项目主题")
    private Integer subjectId;
    @ApiModelProperty("项目标题")
    private String title;
    private String leaderName;
    private String leaderPhone;
    @ApiModelProperty("项目所属学院")
    private DepartEnum departId;
    private String leaderClass;
    @ApiModelProperty("院级或校级立项")
    private ProjectLabel label;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
