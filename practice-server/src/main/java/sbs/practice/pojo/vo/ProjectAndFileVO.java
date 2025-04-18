package sbs.practice.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.common.enums.FileLabel;
import sbs.practice.common.enums.FileType;
import sbs.practice.common.enums.ProjectLabel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "返回项目与文件合并的对象")
public class ProjectAndFileVO implements Serializable {
    @ApiModelProperty("项目id")
    private Integer id;
    @ApiModelProperty("负责人学号")
    private String leaderId;
    @ApiModelProperty("项目主题")
    private Integer subjectId;
    @ApiModelProperty("项目标题")
    private String title;
    private String leaderName;
    private String leaderPhone;
    @ApiModelProperty("项目所属学院")
    private DepartEnum departId;
    private String leaderClass;
    @ApiModelProperty("院级或校级立项")
    private ProjectLabel label;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @ApiModelProperty("文件类型")
    private FileType type;
    @ApiModelProperty("储存文件名")
    private String fileName;
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("文件审核状态")
    @TableField("label")
    private FileLabel fileLabel;
    @ApiModelProperty("审核时间")
    private LocalDateTime examineTime;
    @ApiModelProperty("文件id")
    private Integer fileId;

}
