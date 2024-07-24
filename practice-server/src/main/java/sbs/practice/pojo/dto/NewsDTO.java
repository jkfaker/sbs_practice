package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "学生上传新闻稿类型")
public class NewsDTO implements Serializable {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面图名称")
    private String coverImage;
    @ApiModelProperty("html富文本")
    private String text;
}
