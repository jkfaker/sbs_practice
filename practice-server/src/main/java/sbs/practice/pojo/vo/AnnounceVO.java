package sbs.practice.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceVO {

    private Integer id;
    private String teacherName;
    private String text;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isRead;
    private Short messageType;
    private Integer label;
}
