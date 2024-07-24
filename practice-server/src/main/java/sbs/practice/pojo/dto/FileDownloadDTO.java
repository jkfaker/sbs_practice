package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件下载请求
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "文件下载请求")
public class FileDownloadDTO implements Serializable {
    @ApiModelProperty("项目id")
    private Integer id;
    // TODO:Others TODO
}
