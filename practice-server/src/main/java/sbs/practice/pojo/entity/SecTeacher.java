package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author author
 * @since 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sec_teacher")
@ApiModel(value="二级学院老师", description="")
public class SecTeacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("教师工号")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    @ApiModelProperty("教师姓名")
    private String name;
    @ApiModelProperty("教师所属部门编号")
    private Integer departId;
}
