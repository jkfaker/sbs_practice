package sbs.practice.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "风采展示VO")
public class NewsVO implements Serializable {
    @ApiModelProperty("项目id")
    private Integer projectId;
    @ApiModelProperty("封面图名称")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("负责人姓名")
    private String leaderName;
    @ApiModelProperty("发布时间")
    private LocalDateTime uploadTime;
}

