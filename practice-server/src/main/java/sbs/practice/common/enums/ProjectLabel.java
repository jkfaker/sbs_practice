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



    /**
     * 根据 code 获取对应的 Info 字符串
     *
     * @param label 文件标签的代码
     * @return 对应的 Info 字符串，如果找不到则返回 null
     */
    public static String getInfoByLabel(Integer label) {
        for (ProjectLabel projectLabel : ProjectLabel.values()) {
            if (projectLabel.getLabel().equals(label)) {
                return projectLabel.getInfo();
            }
        }
        return null; // 或者抛出异常，取决于你的需求
    }
}
