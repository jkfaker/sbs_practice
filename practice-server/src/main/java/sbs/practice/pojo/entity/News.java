package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.CrossOrigin;
import sbs.practice.common.enums.NewsLabel;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-07-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news")
@ApiModel(value="News对象", description="新闻稿实体")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @TableField("title")
    @ApiModelProperty("标题")
    private String title;

    @TableField("project_id")
    @ApiModelProperty("对应的项目id")
    private Integer projectId;

    @TableField("cover_image")
    @ApiModelProperty("封面图名称")
    private String coverImage;

    @TableField("label")
    @ApiModelProperty("审核状态")
    private NewsLabel label;

    @TableField("text")
    @ApiModelProperty("html富文本")
    private String text;

    @TableField("upload_time")
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;

    @TableField("examine_time")
    @ApiModelProperty("审核时间")
    private LocalDateTime examineTime;
}
