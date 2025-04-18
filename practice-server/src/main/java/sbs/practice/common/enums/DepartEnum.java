package sbs.practice.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.exception.EnumFindException;

@Getter
@AllArgsConstructor
public enum DepartEnum {
    ADMIN(0, "管理员"),
    GRAMMAR(1, "文法学院"),
    INFO(2, "商务信息学院"),
    ECONOMY(3, "商务经济学院"),
    FOREIGN(4, "商务外语学院"),
    ART(5, "艺术设计学院"),
    HOTEL(6, "酒店管理学院"),
    BUSINESS(7, "工商管理学院");


    @EnumValue
    private final Integer departId;
    @JsonValue
    private final String departName;

    public static DepartEnum getDepartByName(String departName) {
        for (DepartEnum depart : DepartEnum.values()) {
            if (depart.getDepartName().equals(departName)) {
                return depart;
            }
        }
        throw new EnumFindException(MessageConstant.ENUM_FIND_FAILED);
    }
}
