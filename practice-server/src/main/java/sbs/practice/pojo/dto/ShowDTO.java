package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.pojo.query.PageQuery;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("风采展示查询DTO")
public class ShowDTO extends PageQuery {
    @ApiModelProperty("查询条件")
    private String condition;
    @ApiModelProperty("筛选newsLabel")
    private Integer newsLabel;
}
