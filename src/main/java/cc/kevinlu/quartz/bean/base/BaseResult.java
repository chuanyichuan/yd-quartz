package cc.kevinlu.quartz.bean.base;

import java.io.Serializable;

import cc.kevinlu.quartz.error.IError;
import lombok.Data;

/**
 * 通用应答对象
 *
 * @author cc
 */
@Data
public class BaseResult<T> implements Serializable {
    private static final long  serialVersionUID = -3728745929853763101L;

    /**
     * 状态码(200:成功)
     */
    private String             code;
    /**
     * 描述
     */
    private String             message;
    /**
     * 返回参数(当code为200才会有值)
     */
    private T                  data;

    public static final String CODE_SUCCESS     = "200";
    public static final String CODE_FAIL        = "500";

    public static final String MSG_SUCCESS      = "成功";

    public BaseResult() {
        super();
    }

    public BaseResult(T data) {
        super();
        this.data = data;
    }

    public BaseResult(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public BaseResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResult success() {
        return new BaseResult(CODE_SUCCESS, MSG_SUCCESS);
    }

    public static BaseResult success(Object value) {
        return new BaseResult(CODE_SUCCESS, MSG_SUCCESS, value);
    }

    public static BaseResult fail(String message) {
        return new BaseResult(CODE_FAIL, message);
    }

    public static BaseResult fail(IError error) {
        return new BaseResult(error.getCode(), error.getMessage());
    }

    public static BaseResult fail(String code, String message) {
        return new BaseResult(code, message);
    }

}
