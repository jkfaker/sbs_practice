package sbs.practice.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JudgeExist {
    EXIST(1, "已存在"),
    NOT_EXIST(2, "不存在"),
    ;
    @JsonValue
    @EnumValue
    private final Integer code;
    private final String Info;

}

