package sbs.practice.annotation;


import sbs.practice.common.enums.AnnounceLabel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemMessage {
    // 系统消息的title类型： "立项"、"文件上传"
    AnnounceLabel value();
}
