package sbs.practice.common.result;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        Result result = Result.builder()
                .code(1)
                .msg("success")
                .build();
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result result = Result.builder()
                .code(1)
                .msg("success")
                .data(data)
                .build();
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = Result.builder()
                .code(0)
                .msg(msg)
                .build();
        return result;
    }


}
