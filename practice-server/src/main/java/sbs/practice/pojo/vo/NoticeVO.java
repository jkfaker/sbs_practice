package sbs.practice.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NoticeVO", description="")
public class NoticeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("发布者姓名")
    private String teacherName;
    @ApiModelProperty("通知标题")
    private String title;
    @ApiModelProperty("通知封面名称")
    private String coverImage;
    @ApiModelProperty("通知正文html")
    private String text;
    @ApiModelProperty("通知附件")
    private String fileName;
    @ApiModelProperty("通知创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("通知更新时间")
    private LocalDateTime updateTime;
}
