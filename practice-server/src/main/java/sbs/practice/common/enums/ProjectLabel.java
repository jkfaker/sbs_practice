package sbs.practice.common.enums;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 项目的院级立项和校级立项的标签
 */
@Getter
@AllArgsConstructor
public enum ProjectLabel {
    SCHOOL(1, "校级立项"),
    DEPART(2, "院级立项");

    @EnumValue
    private final Integer label;
    @JsonValue
    private final String info;
}
