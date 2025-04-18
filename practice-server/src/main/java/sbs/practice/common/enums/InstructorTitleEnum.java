package sbs.practice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@ApiModel(description = "指导老师职称枚举")
public enum InstructorTitleEnum {
    ASSISTANT(1, "助教"),
    LECTURER(2, "讲师"),
    ASSOCIATE_PROFESSOR(3, "副教授"),
    PROFESSOR(4, "教授");
    @EnumValue
    private final Integer code;
    @JsonValue
    private final String Info;
}
