package sbs.practice.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel(description = "打卡类型枚举类")
@AllArgsConstructor
@Getter
public enum DateInfo {
    SIGN_IN(1, "已打卡"),
    NOT_SIGN(0, "未打卡");
    @EnumValue
    private Integer code;
    @JsonValue
    private String info;
}
