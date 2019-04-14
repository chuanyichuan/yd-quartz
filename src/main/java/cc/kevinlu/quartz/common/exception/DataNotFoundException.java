package cc.kevinlu.quartz.common.exception;

import cc.kevinlu.quartz.error.CommonError;
import cc.kevinlu.quartz.error.IError;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据不存在
 * 
 * @author: cc
 **/
public class DataNotFoundException extends RuntimeException {

    @Getter
    @Setter
    private String code = CommonError.DATA_NOT_EXISTS_ERROR.getCode();

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DataNotFoundException(IError error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

}
