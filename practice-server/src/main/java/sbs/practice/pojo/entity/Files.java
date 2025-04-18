package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import sbs.practice.common.enums.FileLabel;
import sbs.practice.common.enums.FileType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2024-06-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("files")
@ApiModel(value = "上传的材料", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("唯一id")
    private Integer id;
    @ApiModelProperty("对应的项目id")
    private Integer projectId;
    @ApiModelProperty("文件类型")
    private FileType type;
    @ApiModelProperty("储存文件名")
    private String fileName;
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("审核状态")
    private FileLabel label;
    @ApiModelProperty("审核时间")
    private LocalDateTime examineTime;
}
