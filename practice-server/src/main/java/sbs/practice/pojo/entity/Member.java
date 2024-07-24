package sbs.practice.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiuQIDuo
 * @since 2024-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("member")
@ApiModel(value="参与项目的成员", description="")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "project_id", type = IdType.INPUT)
    private Integer projectId;

    private String memberId;

    private String memberName;

    private String memberPhone;

    private LocalDateTime createTime;


}
