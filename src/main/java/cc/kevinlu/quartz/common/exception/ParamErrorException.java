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
public class ParamErrorException extends RuntimeException {

    @Getter
    @Setter
    private String code = CommonError.PARAM_LENGTH_NOT_ALLOW.getCode();

    public ParamErrorException(String message) {
        super(message);
    }

    public ParamErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamErrorException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ParamErrorException(IError error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

}
