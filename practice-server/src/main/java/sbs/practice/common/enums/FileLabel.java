package sbs.practice.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 学生上传文件老师审核标签,返回code给前端
 */
@Getter
@AllArgsConstructor
public enum FileLabel {
    PENDING(1, "待审核"),
    PASSED(2, "已通过"),
    FAILED(3, "已被驳回"),
    ;
    @JsonValue
    @EnumValue
    private final Integer code;
    private final String Info;


    /**
     * 根据 code 获取对应的 Info 字符串
     *
     * @param code 文件标签的代码
     * @return 对应的 Info 字符串，如果找不到则返回 null
     */
    public static String getInfoByCode(Integer code) {
        for (FileLabel label : FileLabel.values()) {
            if (label.getCode().equals(code)) {
                return label.getInfo();
            }
        }
        return null; // 或者抛出异常，取决于你的需求
    }
}
