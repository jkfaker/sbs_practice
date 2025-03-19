package sbs.practice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AnnounceLabel {
    SET_UP_PROJECT(1, "立项申请"),
    MID_TERM_PROJECT(2, "项目中期文件"),
    END_TERM_PROJECT(3, "项目结项"),
    NEWS(4, "新闻稿审核")
    ;
    @EnumValue
    private Integer code;
    @JsonValue
    private String info;
}