package sbs.practice.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举老师类型
 */
@Getter
@AllArgsConstructor
public enum UserType {
    TEACHER(1, "老师"),
    STUDENT(2, "学生");
    @EnumValue
    @JsonValue
    private Integer code;
    private String info;
}
