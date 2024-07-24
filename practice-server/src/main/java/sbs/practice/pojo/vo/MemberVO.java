package sbs.practice.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description="参与项目的成员")
public class MemberVO implements Serializable {
    @ApiModelProperty("成员id")
    private String memberId;
    @ApiModelProperty("成员名称")
    private String memberName;
}