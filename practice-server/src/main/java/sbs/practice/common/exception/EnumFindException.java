package sbs.practice.common.exception;

/**
 * 该异常为enum值不存在时
 */
public class EnumFindException extends BaseException {
    public EnumFindException() {
    }


    public EnumFindException(String msg) {
        super(msg);
    }
}
