package sbs.practice.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.NewsLabel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "风采展示VO")
public class NewsVO implements Serializable {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("项目id")
    private Integer projectId;
    @ApiModelProperty("封面图名称")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("html内容")
    private String text;
    @ApiModelProperty("负责人姓名")
    private String leaderName;
    @ApiModelProperty("发布时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("标签")
    private NewsLabel label;
    @ApiModelProperty("驳回理由")
    private String rejectReason;
}

