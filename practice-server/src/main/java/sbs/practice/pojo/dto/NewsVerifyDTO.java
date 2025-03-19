package sbs.practice.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "老师审核DTO")
public class NewsVerifyDTO {
    @ApiModelProperty("id")
    private Integer id;

    @TableField("label")
    @ApiModelProperty("标签")
    private Integer label;

    @TableField("reject_reason")
    @ApiModelProperty("驳回理由")
    private String rejectReason;

}
