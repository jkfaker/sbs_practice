package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import sbs.practice.common.enums.DateInfo;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("date")
@ApiModel(value="Date对象", description="")
public class Date implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private String date;
    @ApiModelProperty("打卡状态")
    private DateInfo info;


}
