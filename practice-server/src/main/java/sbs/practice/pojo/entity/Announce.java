package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiuQIDuo
 * @since 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("announce")
@ApiModel(value="Announce对象", description="消息实体")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announce implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("消息id")
    private Integer id;
    @ApiModelProperty("接收消息的项目id")
    private Integer projectId;
    @ApiModelProperty("发送消息的教师id")
    private String teacherId;
    @ApiModelProperty("消息内容")
    private String text;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("是否已读")
    private Boolean isRead;
    @ApiModelProperty("消息类型")
    private Short messageType;
    @ApiModelProperty("标签")
    private Integer label;

}
