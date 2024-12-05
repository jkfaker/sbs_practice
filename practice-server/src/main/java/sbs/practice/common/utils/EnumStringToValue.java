package sbs.practice.common.utils;

import lombok.extern.slf4j.Slf4j;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.enums.DepartEnum;
import sbs.practice.common.exception.EnumFindException;

/**
 * 该类致力于实现enum类中value与enum的转换
 */
@Slf4j
public class EnumStringToValue {
    public static DepartEnum getEnumByValue(String departName) {
        try {
            DepartEnum departEnum = DepartEnum.valueOf(departName);

            return departEnum;

        } catch (Exception e) {
            throw new EnumFindException(MessageConstant.ENUM_FIND_FAILED);
        }
    }
}
