package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import sbs.practice.common.enums.InstructorTitleEnum;

/**
 * <p>
 * 指导老师
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-04-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("instructor")
@ApiModel(value="Instructor对象", description="指导老师")
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("project_id")
    private Integer projectId;

    @TableField("name")
    @ApiModelProperty("姓名")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("depart_name")
    @ApiModelProperty("老师所属部门")
    private String departName;

    @TableField("title")
    @ApiModelProperty("指导老师职称")
    private InstructorTitleEnum title;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
