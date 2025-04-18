package sbs.practice.common.exception;

public class OauthException extends RuntimeException {
    public OauthException() {

    }

    public OauthException(String msg) {
        super(msg);
    }
}
