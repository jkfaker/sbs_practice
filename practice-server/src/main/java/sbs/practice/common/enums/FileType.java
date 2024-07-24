package sbs.practice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 材料的类型
 */
@Getter
@AllArgsConstructor
public enum FileType {
    START(1, "立项材料"),
    MIDDLE(2, "中期材料"),
    END(3,"结项材料"),
;

    @EnumValue
    private final Integer typeId;
    @JsonValue
    private final String info;
}
