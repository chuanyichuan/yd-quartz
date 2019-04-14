package cc.kevinlu.quartz.common.exception;

import cc.kevinlu.quartz.error.IError;
import lombok.Getter;
import lombok.Setter;

/**
 * 冲突异常
 * 
 * @author: cc
 **/
public class ConflictException extends RuntimeException {

    @Getter
    @Setter
    private String code;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ConflictException(IError iError) {
        super(iError.getMessage());
        this.code = iError.getCode();
    }
}
