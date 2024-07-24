package sbs.practice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewsLabel {
    PENDING(1, "待审核"),
    PASSED(2,"已通过"),
    FAILED(3, "已驳回"),
    ;
    @JsonValue
    @EnumValue
    private final Integer code;
    private final String Info;

}