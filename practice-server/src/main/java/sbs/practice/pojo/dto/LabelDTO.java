package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 接收老师给项目打标签
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "给项目打标签")
public class LabelDTO implements Serializable {
    @ApiModelProperty("文件id")
    private Integer id;
    @ApiModelProperty("标签")
    private Integer label;
}
